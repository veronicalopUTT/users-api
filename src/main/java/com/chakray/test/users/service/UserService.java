package com.chakray.test.users.service;

import com.chakray.test.users.model.Address;
import com.chakray.test.users.model.User;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import com.chakray.test.users.dto.CreateUserRequest;
import com.chakray.test.users.dto.UpdateUserRequest;
import com.chakray.test.users.exception.UserNotFoundException;
import com.chakray.test.users.security.AesEncryptionService;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.UUID;


@Service
public class UserService {
    private  final List<User> users = new ArrayList<>();

    private static final ZoneId MADAGASCAR_ZONE =
            ZoneId.of("Indian/Antananarivo");

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    private final AesEncryptionService encryptionService;

    public UserService(AesEncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }

    @PostConstruct
    public void init() {
        users.add(buildUser(
                "messi10@mail.com",
                "messi10",
                "+52 664 897 5427",
                "password2",
                "MESSI100202YYY"

        ));

        users.add(buildUser(
                "CR7@mail.com",
                "CR7",
                "+52 664 987 0972",
                "password1",
                "CRIR770101XXX"
        ));

        users.add(buildUser(
                "mbape@mail.com",
                "admin",
                "+52 664 987 2345",
                "password3",
                "MBAP100303ZZZ"
        ));
    }
    private  User buildUser(String email,
                            String name,
                            String phone,
                            String password,
                            String taxId) {
        return User.builder()
                .id(UUID.randomUUID())
                .email(email)
                .name(name)
                .phone(phone)
                .password(
                        encryptionService.encrypt(password)
                )
                .taxId(taxId)
                .createdAt(nowMadagascar())
                .addresses(sampleAddresses())
                .build();
    }
    private String nowMadagascar() {
        return ZonedDateTime
                .now(MADAGASCAR_ZONE)
                .format(FORMATTER);
    }

    private List<Address> sampleAddresses() {
        return List.of(
                Address.builder()
                        .id(1L)
                        .name("workaddress")
                        .street("street No. 1")
                        .countryCode("UK")
                        .build(),
                Address.builder()
                        .id(2L)
                        .name("homeaddress")
                        .street("street No. 2")
                        .countryCode("AU")
                        .build()
        );
    }
    public List<User> getAll() {
        return users;
    }

    public List<User> getAllSorted(String field) {

        Comparator<User> comparator;

        switch (field) {
            case "id" -> comparator = Comparator.comparing(User::getId);
            case "email" -> comparator = Comparator.comparing(User::getEmail);
            case "name" -> comparator = Comparator.comparing(User::getName);
            case "phone" -> comparator = Comparator.comparing(User::getPhone);
            case "tax_id" -> comparator = Comparator.comparing(User::getTaxId);
            case "created_at" -> comparator = Comparator.comparing(User::getCreatedAt);
            default -> throw new IllegalArgumentException(
                    "Invalid sortedBy field: " + field);
        }

        return users.stream()
                .sorted(comparator)
                .toList();
    }

    public User createUser(CreateUserRequest request) {

        User user = User.builder()
                .id(UUID.randomUUID())
                .email(request.getEmail())
                .name(request.getName())
                .phone(request.getPhone())
                .password(
                        encryptionService.encrypt(request.getPassword())
                )
                .taxId(request.getTaxId())
                .createdAt(nowMadagascar())
                .addresses(
                        request.getAddresses().stream()
                                .map(a -> Address.builder()
                                        .id(a.getId())
                                        .name(a.getName())
                                        .street(a.getStreet())
                                        .countryCode(a.getCountryCode())
                                        .build())
                                .toList()
                )
                .build();

        users.add(user);

        return user;
    }

    public User findByEmail(String email) {

        return users.stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElseThrow(() ->
                        new UserNotFoundException("User not found with email: " + email)
                );
    }

    public List<User> filterUsers(String filter) {

        String[] parts = filter.split(",");

        if (parts.length != 3) {
            throw new IllegalArgumentException(
                    "Filter must be: field+op+value");
        }

        String field = parts[0];
        String op = parts[1];
        String value = parts[2];

        return users.stream()
                .filter(user -> matches(user, field, op, value))
                .toList();
    }
    private boolean matches(
            User user,
            String field,
            String op,
            String value
    ) {

        String fieldValue = switch (field) {
            case "email" -> user.getEmail();
            case "name" -> user.getName();
            case "phone" -> user.getPhone();
            case "tax_id" -> user.getTaxId();
            case "created_at" -> user.getCreatedAt();
            case "id" -> user.getId().toString();
            default -> throw new IllegalArgumentException(
                    "Invalid filter field: " + field);
        };

        return switch (op) {
            case "co" -> fieldValue.contains(value);
            case "sw" -> fieldValue.startsWith(value);
            case "ew" -> fieldValue.endsWith(value);
            case "eq" -> fieldValue.equals(value);
            default -> throw new IllegalArgumentException(
                    "Invalid filter operator: " + op);
        };
    }

    public User updateUser(String id, UpdateUserRequest request) {

        User user = users.stream()
                .filter(u -> u.getId().toString().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new UserNotFoundException("User not found with id: " + id)
                );

        if (request.getEmail() != null)
            user.setEmail(request.getEmail());

        if (request.getName() != null)
            user.setName(request.getName());

        if (request.getPhone() != null)
            user.setPhone(request.getPhone());

        if (request.getTaxId() != null)
            user.setTaxId(request.getTaxId());

        if (request.getPassword() != null)
            user.setPassword(
                    encryptionService.encrypt(request.getPassword())
            );

        return user;
    }

    public void deleteUser(String id) {

        boolean removed = users.removeIf(
                u -> u.getId().toString().equals(id)
        );

        if (!removed) {
            throw new UserNotFoundException(
                    "User not found with id: " + id
            );
        }
    }

}