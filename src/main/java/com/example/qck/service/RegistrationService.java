package com.example.qck.service;

import com.example.qck.config.MyUserDetails;
import com.example.qck.model.Role;
import com.example.qck.model.School;
import com.example.qck.model.Subject;
import com.example.qck.model.User;
import com.example.qck.pojo.EduGroupPojo;
import com.example.qck.pojo.UserPojo;
import com.example.qck.repository.SchoolRepository;
import com.example.qck.repository.SubjectRepository;
import com.example.qck.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final SubjectRepository subjectRepository;
    private final SchoolRepository schoolRepository;
    private final ParameterizedTypeReference<HashMap<String, String>> responseType = new ParameterizedTypeReference<>() {};

    public void register(User user) throws IOException {
        RequestEntity<Void> request  = RequestEntity
                .post("http://localhost:8000/v.2/login?username="+user.getUsername() + "&password=" + user.getPassword())
                .accept(MediaType.APPLICATION_JSON).build();
        Map<String, String> jsonDictionary  = restTemplate.exchange(request, responseType).getBody();
        if(jsonDictionary.get("token") != null){
            String token = jsonDictionary.get("token");
            getData(user, token);
        }
    }

    public void getData(User user, String token) throws IOException {
        UserPojo userData = new UserPojo();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<UserPojo> entity = new HttpEntity<>(userData,headers);
        String s = restTemplate.exchange("http://localhost:8000/v2.0/users/me/context?token="+token, HttpMethod.GET, entity, String.class).getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        UserPojo userPojo = objectMapper.readValue(s, UserPojo.class);
        if(userRepository.findById(userPojo.getUserId()).isPresent()) {
            return;
        }

        createUser(userPojo, user);
    }

    public void createUser(UserPojo userPojo, User user){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User newUser = new User();
        ArrayList<Role> roles = new ArrayList<>();
        Role role = new Role();
        List<Integer> studyYears = new ArrayList<>();
        Subject subject = null;
        School school = new School();

        if(userPojo.getRoles().equals("student")) {
            role.setId(2L);
            role.setName("USER");
        }
        else if(userPojo.getRoles().equals("teacher")) {
            subject = subjectRepository.getOne(userPojo.getSubject().longValue());
            role.setId(3L);
            role.setName("TEACHER");
        }
        roles.add(role);
        for (EduGroupPojo eduGroupPojo : userPojo.getEduGroups()){
            if(studyYears.contains(eduGroupPojo.getStudyyear())) continue;
            studyYears.add(eduGroupPojo.getStudyyear());
        }

        Optional<School> optionalSchool = schoolRepository.findById(userPojo.getSchools().get(0).getId());

        if(optionalSchool.isPresent()){
            school = schoolRepository.findById(userPojo.getSchools().get(0).getId()).get();
        }else {
            school.setId(userPojo.getSchools().get(0).getId());
            school.setName(userPojo.getSchools().get(0).getName());
            school.setRegion(userPojo.getSchools().get(0).getRegion());
            schoolRepository.save(school);
        }

        newUser.setId(userPojo.getUserId());
        newUser.setUsername(user.getUsername());
        newUser.setPassword(encoder.encode(user.getPassword()));
        newUser.setRoles(roles);
        newUser.setEnabled(true);
        newUser.setStudyYears(studyYears);
        newUser.setSubject(subject);
        newUser.setSchool(school);
        userRepository.save(newUser);

    }

}
