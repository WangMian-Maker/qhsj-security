package com.example.demo.repository;

import com.example.demo.entity.StaffInfor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StaffInforRepository extends JpaRepository<StaffInfor,Long> {
//    private Long sid;
//    private String staffName;
//    private String sex;
//    private String birthday;
//    private String nature;
//    private String outlook;//政治面貌
//    private String department;
//    private String position;
//    private String beginWorkTime;
//    private String idCard;
//    private String phoneNumber;
//    private String mail;
//    private String marriage;
//    private String birthPosition;
//    private String currentPosition;
    @Modifying
    @Query(value = "insert into staff_infor (staff_id,staff_name,sex,birthday,nature,outlook,position,begin_work_time,id_card,phone_number,mail,marriage,birth_position,current_position,census_register,department_did) " +
            "values(?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11,?12,?13,?14,?15,?16)",nativeQuery = true)
    public void save(Long staffId,String staffName,String sex,String birthday,String nature,String outlook,String position,
                     String beginWorkTime,String idCard,String phoneNumber,String mail,String marriage,String birthPosition,String currentPosition,String censusRegister,Long did);

    @Query(value = "select max(s.staffId) from StaffInfor s")
    public Long maxId();

    //@Modifying
    //@Query(value = "update staff_infor set state='0' where sid=?1",nativeQuery = true)
    //public void deleteById(Long id);

    public StaffInfor findBystaffId(Long staffId);

    @Query(value = "select s from StaffInfor s where s.staffName like ?1")
    public List<StaffInfor> findByStaffName(String staffNameLike);

    @Query(value = "select s from StaffInfor s where s.department like ?1")
    public List<StaffInfor> findByDepartment(String department);

    @Query(value = "select s from StaffInfor s where s.department.did=?1 and s.staffName like ?2")
    public List<StaffInfor> findByStaffNameInDepartment(Long did,String staffName);

    @Query(value = "select distinct s.department from StaffInfor s")
    public List<String> findAllDepartment();
//    @Query(value = "select s from StaffInfor s where s.sysUser.uid=?1")
//    public List<StaffInfor> findByUser(Long uid);
}
