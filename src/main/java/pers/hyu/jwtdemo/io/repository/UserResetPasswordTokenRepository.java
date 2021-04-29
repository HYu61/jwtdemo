package pers.hyu.jwtdemo.io.repository;

import org.springframework.data.repository.CrudRepository;
import pers.hyu.jwtdemo.io.entity.UserEntity;
import pers.hyu.jwtdemo.io.entity.UserResetPasswordToken;

public interface UserResetPasswordTokenRepository extends CrudRepository<UserResetPasswordToken, Long> {
    void deleteAllByUserEntity(UserEntity userEntity);
}
