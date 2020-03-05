package com.redhat.training.microprofile.myschool.rest;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.redhat.training.microprofile.myschool.model.Student;

@Path("/students")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class StudentEndpoint {
	
	private Map<Integer,Student> studentsStructure = new HashMap<>();

	@GET
    public Response getStudents() {
        return Response.status(Response.Status.OK).entity(studentsStructure.values()).build();
    }
	
	@GET
	@Path("{rollNumber}")
    public Response getStudent(@PathParam(value = "rollNumber") Integer rollNumber) {
		
		Student student = studentsStructure.get(rollNumber);
		if(student == null) {
			return Response.status(Response.Status.OK).entity(studentsStructure.values()).build();			
		}

        return Response.status(Response.Status.OK).entity(student).build();
    }
     
    @POST
    public Response postStudent(Student student) {
        int rollNumber = student.getRollNumber();
        if(rollNumber == 0) {
        	rollNumber = (int)(Math.random() * 50000 + 1);
        	if(studentsStructure.get(rollNumber) != null) {
        		return postStudent(student);
        	}
        	student.setRollNumber(rollNumber);
        }       
        studentsStructure.put(rollNumber, student);
        String result = "Record entered: "+ student.getRollNumber() + " Name: " + student.getFirstName() + " LastName: " + student.getLastName();
        return Response.status(Response.Status.CREATED).entity(result).build();
    }
    
    @DELETE
    @Path("{rollNumber}")
	public Response deleteStudent(@PathParam(value = "rollNumber") Integer rollNumber) {
    	Student student = studentsStructure.remove(rollNumber);
        return Response.status(Response.Status.NO_CONTENT).entity(student).build();
    }
}
