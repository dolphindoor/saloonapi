/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saloon.services;

import com.dolphindoors.resource.jaxrs.PageableRequest;
import com.dolphindoors.resource.jaxrs.PagedResult;
import com.dolphindoors.resource.jpa.CrudApi;
import com.dolphindoors.resource.jpa.Pagination;
import com.dolphindoors.resource.jpa.QueryBuilder;
import com.dolphindoors.resource.utilities.DateUtil;
import com.dolphindoors.resource.utilities.JUtils;
import com.dolphindoors.resource.utilities.Pattern;
import com.saloon.dto.AttendanceDto;
import com.saloon.dto.EmployeeDto;
import com.saloon.entity.Attendance;
import com.saloon.entity.Employee;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Richard Narh
 */
@Stateless
public class EmployeeService {
    @Inject private CrudApi crudApi;
    @Inject private DefaultService ds;
    
   private static final String ROOT_PATH = System.getProperty("os.name").toLowerCase().contains("mac") || System.getProperty("os.name").toLowerCase().contains("win") ?
    System.getProperty("user.home") + "/Documents/orah/attendance/"+LocalDate.now()+"/" : "/var/orah/attendance/"+LocalDate.now()+"/";

    public EmployeeDto save(EmployeeDto dto) {
        Employee employee = crudApi.save(toEntity(dto));
        if(dto.getId() == null){
            String text = "Please use this link to record your attendance \n"
                 + "Link: https://orahspalon.netlify.app \n"
                 + "User Code: "+employee.getUserCode()+" \n"
                 + "do not share your user code";
            ds.sms(List.of(employee.getPhoneNumber()), text);
        }
        return toDTO(employee);
    }

    public EmployeeDto findById(String employeeId) {
        Employee employee = crudApi.find(Employee.class, employeeId);
        return toDTO(employee);
    }
    
    public boolean revoke(String employeeId) {
        Employee employee = crudApi.find(Employee.class, employeeId);
        employee.setUserCode(null);
        return crudApi.save(employee) != null;
    }

    public PagedResult<EmployeeDto> fetchEmployees(PageableRequest pageable) {
        Pagination<Employee> pager = new Pagination<>(Employee.class, crudApi.getEm());
        QueryBuilder<Employee> builder = Employee.query(crudApi.getEm());
        List<EmployeeDto> dtoList = pager.getPage(pageable, builder)
                .stream()
                .map(employee -> toDTO(employee))
                .collect(Collectors.toList());
        long total = builder.count();
        int totalPages = (int)Math.ceil((double)total / pageable.getSize());
        return new PagedResult<>(dtoList, pageable.getPage(), pageable.getSize(), total, totalPages);
    }

    public PagedResult<EmployeeDto> fetchEmployees(String filter, PageableRequest pageable) {
        Pagination<Employee> pager = new Pagination<>(Employee.class, crudApi.getEm());
        Consumer<QueryBuilder<Employee>> builder = qb -> {
            String searchKey = filter != null ? filter.trim() : "";
            if(!searchKey.isEmpty()){
                qb.andWhereLikeIgnoreCase(Employee._phoneNumber, "%" + searchKey + "%");
                qb.orWhereLikeIgnoreCase(Employee._fullName, "%" + searchKey + "%");
            }
        };
        
        List<EmployeeDto> dtoList = pager.getPage(pageable, builder)
                .stream()
                .map(product -> toDTO(product))
                .collect(Collectors.toList());
        long total = pager.getCount(builder);
        int totalPages = (int)Math.ceil((double)total / pageable.getSize());
        return new PagedResult<>(dtoList, pageable.getPage(), pageable.getSize(), total, totalPages);
    }
    
    public List<AttendanceDto> fetchAttendance(String date) {
        LocalDate localDate = DateUtil.parseLocalDate(date, Pattern._ddMMyyyy);
        List<Attendance> attendanceList = Attendance.query(crudApi.getEm())
                    .where(Attendance._valueDate, localDate)
                    .list();
        
        List<AttendanceDto> dtoList = new LinkedList<>();
        attendanceList.forEach((attendance ->{
            AttendanceDto dto = new AttendanceDto();
            Employee employee = attendance.getEmployee();
            File file = getFile(attendance.getId());
            try {
            if(file != null){
                dto.setUserImage(Base64.getEncoder().encodeToString(Files.readAllBytes(file.toPath())));
            }
            } catch (IOException e) {
                e.getMessage();
            }
            dto.setId(attendance.getId());
            dto.setTimeRecorded(DateUtil.localDateTimeToString(attendance.getAttendanceTime(), Pattern.hhmma));
            dto.setFullName(employee.getFullName());
            
            dtoList.add(dto);
        }));
        return dtoList;
    }
    
    public File getFile(String attendanceId){
        Attendance attendance = crudApi.find(Attendance.class, attendanceId);
        int lastIndex = 0;
        if(attendance.getUserImage() != null){
            lastIndex = attendance.getUserImage().lastIndexOf('/');
            if (lastIndex != -1) {
                String fileName = attendance.getUserImage().substring(lastIndex + 1);
                File file = new File(ROOT_PATH + fileName);
                return file;
            }
        }
        return null;
    }
    
    
    public boolean deleteEmployee(String employeeId) {
        Employee employee = crudApi.find(Employee.class, employeeId);
        return employee != null && crudApi.delete(employee);
    }
    
    private Employee toEntity(EmployeeDto dto){
        Employee employee = null;
        if(dto.getId() != null){
            employee = crudApi.find(Employee.class, dto.getId());
        }
        else
        {
            employee = new Employee();
            employee.setUserCode(JUtils.generate(5));
        }
        employee.setAddress(dto.getAddress());
        employee.setFullName(dto.getFullName());
        employee.setEmail(dto.getEmail());
        employee.setPhoneNumber(dto.getPhoneNumber());
        employee.setStatus(dto.getStatus());
        return employee;
    }
    
    private EmployeeDto toDTO(Employee employee){
        EmployeeDto dto = new EmployeeDto();
        dto.setId(employee.getId());
        dto.setFullName(employee.getFullName());
        dto.setEmail(employee.getEmail());
        dto.setPhoneNumber(employee.getPhoneNumber());
        dto.setAddress(employee.getAddress());
        dto.setUserCode(employee.getUserCode());
        dto.setStatus(employee.getStatus());
        return dto;
    }
}
