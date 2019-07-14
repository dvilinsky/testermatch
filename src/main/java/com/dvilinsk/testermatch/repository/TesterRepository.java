package com.dvilinsk.testermatch.repository;

import com.dvilinsk.testermatch.DTO.TesterDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TesterRepository extends CrudRepository<TesterDTO, Integer> {

    @Query(value = "SELECT count(*) as numBugs, firstname, lastname FROM tester, bug WHERE tester.testerid = bug.testerid " +
            "GROUP BY tester.testerid ORDER BY numBugs DESC", nativeQuery = true)
    List<TesterDTO> findAllTesters();

    @Query(value = "select count(*) AS numBugs, firstname, lastname from tester, bug where tester.testerid = bug.testerId and " +
            "lower(country) in :countries group by tester.testerid ORDER BY numBugs DESC", nativeQuery = true)
    List<TesterDTO> findByCountry(@Param("countries") List<String> countries);

    @Query(value = "SELECT count(*) AS numBugs, firstname, lastname FROM tester, device, bug WHERE tester.testerId = bug.testerId " +
            "AND device.deviceid = bug.deviceid AND lower(device.description) in :devices GROUP BY tester.testerid ORDER BY numBugs DESC",
            nativeQuery = true)
    List<TesterDTO> findByDevice(@Param("devices") List<String> devices);

    @Query(value = "select count(*) AS numBugs, firstname, lastname from tester, device, bug where tester.testerId = bug.testerId " +
            "and device.deviceid = bug.deviceid and lower(device.description) in :devices and lower(tester.country) in :countries group by tester.testerid " +
            "ORDER BY numBugs DESC;",
            nativeQuery = true)
    List<TesterDTO> findByDeviceAndCountry(@Param("devices") List<String> devices, @Param("countries") List<String> countries);
}
