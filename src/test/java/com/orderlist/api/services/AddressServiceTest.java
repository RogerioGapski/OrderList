package com.orderlist.api.services;

import com.orderlist.api.exceptions.customs.ConflictException;
import com.orderlist.api.exceptions.customs.NotFoundException;
import com.orderlist.api.model.dto.request.address.CreateAddressDTO;
import com.orderlist.api.model.dto.request.address.UpdateAddressDTO;
import com.orderlist.api.model.dto.response.AddressDTO;
import com.orderlist.api.model.entities.Address;
import com.orderlist.api.model.entities.User;
import com.orderlist.api.repository.AddressRepository;
import com.orderlist.api.repository.UserRepository;
import com.orderlist.api.utils.mapper.AddressMapper;
import com.orderlist.api.utils.mapper.AddressMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.assertArg;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {

    @Mock
    AddressRepository addressRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    AddressService addressService;

    @Captor
    ArgumentCaptor<Address> addressCaptor;

    AddressMapper addressMapper;

    @Nested
    class createAddress {

        @BeforeEach
        void setup() {
            addressMapper = new AddressMapperImpl();
            addressService = new AddressService(addressRepository, addressMapper, userRepository);
        }

        @Test
        @DisplayName("Should create an address if the user exists")
        void shouldCreateAnAddressIfTheUserExists() {
            addressCaptor = ArgumentCaptor.forClass(Address.class);
            var createAddress = new CreateAddressDTO("New York", "Sweet Town", "1001");
            var user = new User();
            user.setId(UUID.randomUUID());

            when(userRepository.findById(user.getId()))
                    .thenReturn(Optional.of(user));

            AddressDTO result = addressService.createAddress(createAddress, user.getId());

            verify(userRepository).findById(user.getId());
            verify(addressRepository).save(addressCaptor.capture());

            assertThat(addressCaptor.getValue().getCity())
                    .isEqualTo(result.city());

            assertThat(addressCaptor.getValue().getStreet())
                    .isEqualTo(result.street());

            assertThat(addressCaptor.getValue().getNumber())
                    .isEqualTo(result.number());

            assertThat(addressCaptor.getValue().getUser().getId())
                    .isEqualTo(result.userId());
        }

        @Test
        @DisplayName("Should throw exception if the user does not exist")
        void shouldThrowExceptionIfTheUserDoesNotExist() {
            var dto = new CreateAddressDTO("New York", "Sweet Town", "1001");
            var userId = UUID.randomUUID();

            when(userRepository.findById(userId))
                    .thenReturn(Optional.empty());

            NotFoundException e = assertThrows(
                    NotFoundException.class,
                    () -> addressService.createAddress(dto, userId)
            );

            verify(userRepository).findById(userId);
            verify(addressRepository, never()).save(any(Address.class));

            assertThat(e.getMessage())
                    .isEqualTo("User not found");
        }

        @Test
        @DisplayName("Should throw exception if the user has five or more addresses")
        void shouldThrowExceptionIfTheUserHasFiveOrMoreAddresses() {
            var dto = new CreateAddressDTO("New York", "Sweet Town", "1001");
            var userId = UUID.randomUUID();
            var user = new User();
            user.setId(userId);
            user.setAddresses(List.of(new Address(), new Address(), new Address(), new Address(), new Address()));

            when(userRepository.findById(userId))
                    .thenReturn(Optional.of(user));

            ConflictException e = assertThrows(
                    ConflictException.class,
                    () -> addressService.createAddress(dto, userId)
            );

            verify(userRepository).findById(userId);
            verify(addressRepository, never()).save(any(Address.class));

            assertThat(e.getMessage())
                    .isEqualTo("Address limit reached");
        }
    }

    @Nested
    class deleteById {

        @BeforeEach
        void setup() {
            addressMapper = new AddressMapperImpl();
            addressService = new AddressService(addressRepository, addressMapper, userRepository);
        }

        @Test
        @DisplayName("Should delete the address if it exists")
        void shouldDeleteTheAddressIfItExists() {
            var address = new Address(10L, "New York", "Sweet Town", "1001", new User());

            when(addressRepository.findById(address.getId()))
                    .thenReturn(Optional.of(address));

            addressService.deleteById(address.getId());

            verify(addressRepository).findById(address.getId());
            verify(addressRepository).deleteById(address.getId());
        }

        @Test
        @DisplayName("Should throw exception if the address does not exist")
        void shouldThrowExceptionIfTheAddressDoesNotExist() {
            var idInexistent = 10L;

            when(addressRepository.findById(idInexistent))
                    .thenReturn(Optional.empty());

            NotFoundException e = assertThrows(
                    NotFoundException.class,
                    () -> addressService.deleteById(idInexistent)
            );

            verify(addressRepository).findById(idInexistent);
            verify(addressRepository, never()).deleteById(any());

            assertThat(e.getMessage())
                    .isEqualTo("Address not found");
        }
    }

    @Nested
    class findById {

        @BeforeEach
        void setup() {
            addressMapper = new AddressMapperImpl();
            addressService = new AddressService(addressRepository, addressMapper, userRepository);
        }

        @Test
        @DisplayName("Should find the address if it exists")
        void shouldFindTheAddressIfItExists() {
            var user = new User();
            user.setId(UUID.randomUUID());

            var address = new Address(10L, "New York", "Sweet Town", "1001", user);

            when(addressRepository.findById(address.getId()))
                    .thenReturn(Optional.of(address));

            var result = addressService.findById(address.getId());
            var expected = new AddressDTO(user.getId(), address.getId(), address.getCity(), address.getStreet(), address.getNumber());

            verify(addressRepository).findById(address.getId());

            assertThat(result)
                    .usingRecursiveComparison()
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("Should throw exception if the address does not exist")
        void shouldThrowExceptionIfTheAddressDoesNotExist() {
            var idInexistent = 10L;

            when(addressRepository.findById(idInexistent))
                    .thenReturn(Optional.empty());

            NotFoundException e = assertThrows(
                    NotFoundException.class,
                    () -> addressService.deleteById(idInexistent)
            );

            verify(addressRepository).findById(idInexistent);

            assertThat(e.getMessage())
                    .isEqualTo("Address not found");
        }
    }

    @Nested
    class getAddressesByUser {

        @BeforeEach
        void setup(){
            addressMapper = new AddressMapperImpl();
            addressService = new AddressService(addressRepository, addressMapper, userRepository);
        }

        @Test
        @DisplayName("Should return all addresses by user ID if the user exists")
        void shouldReturnAllAddressesByUserIDIfTheUserExists() {
            var user = new User();
            user.setId(UUID.randomUUID());
            user.setAddresses(List.of(new Address(), new Address(), new Address()));

            when(userRepository.findById(user.getId()))
                    .thenReturn(Optional.of(user));

            List<AddressDTO> result = addressService.getAddressesByUser(user.getId());

            verify(userRepository).findById(user.getId());

            assertThat(result)
                    .hasSize(user.getAddresses().size());
        }

        @Test
        @DisplayName("Should throw exception if the user does not exist")
        void shouldThrowExceptionIfTheUserDoesNotExist() {
            var userId = UUID.randomUUID();

            when(userRepository.findById(userId))
                    .thenReturn(Optional.empty());

            NotFoundException e = assertThrows(
                    NotFoundException.class,
                    () -> addressService.getAddressesByUser(userId)
            );

            verify(userRepository).findById(userId);

            assertThat(e.getMessage())
                    .isEqualTo("User not found");
        }
    }

    @Nested
    class updateAddress {

        @BeforeEach
        void setup() {
            addressMapper = new AddressMapperImpl();
            addressService = new AddressService(addressRepository, addressMapper, userRepository);
        }

        @Test
        @DisplayName("Should update the address if it exists")
        void shouldUpdateTheAddressIfItExists() {
            var user = new User();
            user.setId(UUID.randomUUID());

            var dto = new UpdateAddressDTO("Los Angeles", "Rodeo Drive", "1000");
            var addressUp = new Address(10L, "New York", "Sweet Town", "1001", user);

            when(addressRepository.findById(addressUp.getId()))
                    .thenReturn(Optional.of(addressUp));

            when(addressRepository.save(addressUp))
                    .thenReturn(addressUp);

            var result = addressService.updateAddress(dto, addressUp.getId());
            var expected = new AddressDTO(
                    addressUp.getUser().getId(),
                    addressUp.getId(),
                    dto.city(),
                    dto.street(),
                    dto.number());

            verify(addressRepository).findById(addressUp.getId());
            verify(addressRepository).save(
                    assertArg(address -> address.getId().equals(addressUp.getId()))
            );

            assertThat(result)
                    .usingRecursiveComparison()
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("Should throw exception if the address does not exist")
        void shouldThrowExceptionIfTheAddressDoesNotExist() {
            var dto = new UpdateAddressDTO("Los Angeles", "Rodeo Drive", "1000");
            var idInexistent = 10L;

            when(addressRepository.findById(idInexistent))
                    .thenReturn(Optional.empty());

            NotFoundException e = assertThrows(
                    NotFoundException.class,
                    () -> addressService.updateAddress(dto, idInexistent)
            );

            verify(addressRepository).findById(idInexistent);
            verify(addressRepository, never()).save(any(Address.class));

            assertThat(e.getMessage())
                    .isEqualTo("Address not found");
        }
    }
}