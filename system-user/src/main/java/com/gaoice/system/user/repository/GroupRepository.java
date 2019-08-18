package com.gaoice.system.user.repository;

import com.gaoice.system.user.entity.GroupDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRepository extends JpaRepository<GroupDO, Long> {

    List<GroupDO> findByParentId(Long id);

    List<GroupDO> findByParentIdOrderByDisplayOrderAsc(Long id);

}
