package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.model.Address;

public interface AddressService {

    Address findByUserId(String userId);

    Address deleteByUserId(String userId) ;

     void save(Address address) ;

     Address updateByUserId(String userId);
}
