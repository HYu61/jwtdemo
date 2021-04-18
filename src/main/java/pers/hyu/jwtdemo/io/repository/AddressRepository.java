package pers.hyu.jwtdemo.io.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pers.hyu.jwtdemo.io.entity.AddressEntity;

@Repository
public interface AddressRepository extends CrudRepository<AddressEntity, Long> {
    AddressEntity findByAddressId(String addressId);
}
