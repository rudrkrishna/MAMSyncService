package com.example.MAMSyncService.respository;

import com.example.MAMSyncService.dto.Assets;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface AssetsRepository extends CrudRepository<Assets, Integer> {


}
