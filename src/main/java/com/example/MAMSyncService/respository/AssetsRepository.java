package com.example.MAMSyncService.respository;

import com.example.MAMSyncService.dto.Assets;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface AssetsRepository extends CrudRepository<Assets, Integer> {

    /*@Query(value = "select * from assets", nativeQuery = true)
    List<Object[]> insertData();

    @Modifying
    @Query(value = "insert into assets(id,filename, hasMetaData, versionCount) values (:id,:fileName,:metaData,:vCount)", nativeQuery = true)
    @Transactional
    void insertManually(@Param("id") int id, @Param("fileName") String fileName, @Param("metaData") int metaData, @Param("vCount")int vCount);
*/
}
