INSERT INTO company_details (id, account_number,
                             street, city,
                             name, postcode, tax_id)
VALUES (1, '11114015601081110181488249',
        'ul. Przykładowa 21/96', 'Warszawa',
        'Zdrofitus sp. z.o.o', '00-950', '1231231234');

INSERT INTO tax(rate_percent, start_dttm)
VALUES (8, current_timestamp);

insert into administrator_data (id)
values (1);

INSERT INTO public.user_app (id, active, email, administrator_id, password, role, username)
VALUES ('1'::bigint, true::boolean, 'lakomika@gmail.com'::character varying, '1'::bigint,
        '$2a$10$lnq09Ki2XyAcBGBdo00Ex.unpd7M28vntJ3w1D0jWPDv/.upg38KW'::character varying, --password: admin.test
        'ROLE_ADMIN'::character varying, 'admin.test'::character varying) returning id;

SELECT setval('user_app_id_seq', 2, true);

ALTER SEQUENCE user_app_id_seq
    START 2;

SELECT setval('administrator_data_id_seq', 2, true);

ALTER SEQUENCE administrator_data_id_seq
    START 2;