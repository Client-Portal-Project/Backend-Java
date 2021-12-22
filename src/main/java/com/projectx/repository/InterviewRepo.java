package com.projectx.repository;

import com.projectx.model.Applicant;
import com.projectx.model.Interview;
import com.projectx.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterviewRepo extends JpaRepository<Interview, Integer> {

    List<Interview> getListByApplicant(Applicant applicant);

    List<Interview> getListByOrder(Order order);
}
