# GymFit-Backend

Backend of an application supporting gym management.

## Table of contents

* [General info](#general-info)
* [Demo](#demo)
* [Roles](#roles)
* [Technologies](#technologies)
* [Usage technologies](#usage-technologies)

## General info

The application implemented is used to manage gym passes. Nowadays, buying and selling a pass is a basic operation at
sports and recreation facilities. In application are 3 roles:
<ul>
<li><b>Customer</b></li>
<li><b>Receptionist</b></li>
<li><b>Administrator</b></li>
</ul>

A discussion of the possibility of each role is set out [below](#roles).

## Demo

<p>Here is a working live demo : https://lakomika-gym-fit.herokuapp.com/ </p>
<p>(Please wait a moment as the server may be starting up. :smile:) </p>
<p>Example login data for:</p>
<p><b>Administrator:</b></p>
<p>Login: <i>admin.example</i> Password: <i>SuperSecretPassword532#</i></p>
<p><b>Customer:</b></p>
<p>Login: <i>customer.example</i> Password: <i>CustomerSecretPassword532#</i></p>
<p><b>Receptionist:</b></p>
<p>Login: <i>receptionist.example</i> Password: <i>ReceptionistSecretPassword532#</i></p>

## Roles

### Customer can:

<ul>
<li>Possibility to create an account on the public website</li>
<li>Logging into the system</li>
<li>Resetting your password by email</li>
<li>Viewing and editing your personal data</li>
<li>Ordering a pass</li>
<li>Viewing historical invoices</li>
<li>Cancel the order</li>
<li>View the validity of your pass</li>
<li>View your access card</li>
<li>Change your password</li>
<li>View transfer details for an unfinished transaction</li>
</ul>

### Receptionist can:

<ul>
<li>Logging into the system</li>
<li>Password reset via email</li>
<li>Ordering a pass for the customer (the customer can buy a pass on the spot by showing his access card)</li>
<li>Acceptance of payment for the order</li>
<li>Checking the validity status of the pass by the customer's card number</li>
<li>Change your password</li>
<li>Add a customer to the service</li>
<li>Display a list of active and inactive customers</li>
<li>Deactivate a customer's account</li>
<li>Change the password of a specific customer</li>
<li>View the data of an individual customer</li>
</ul>

### Administrator - has access to all the functionalities as the receptionist and addition can:

<ul>
<li>Add a receptionist to the system</li>
<li>Display a list of active and inactive receptionists</li>
<li>Display the data of an individual receptionist</li>
<li>Deactivate the account of a receptionist</li>
<li>Change the password of an individual receptionist</li>
<li>Display the company data</li>
<li>Edit company data</li>
<li>Display the tax rate</li>
<li>Edit tax rate</li>
</ul>

## Technologies

<ul>
<li>Java 13</li>
<li>Spring Boot 2.4.4</li>
<li>Spring Boot Test 2.4.4</li>
<li>Spring Boot Devtools 2.4.4</li>
<li>Spring Data JPA 2.4.4</li>
<li>Spring Security 2.4.4</li>
<li>JWT 0.9.1</li>
<li>PostgreSQL 11 (driver 42.2.19)</li>
<li>Lombok 1.18.12</li>
<li>Log4j 2.13.3</li>
<li>Model Mapper 2.3.9</li>
<li>Javax Persistence 2.2</li>
</ul>

## Usage technologies

<b>Examples of use of technology:</b>

### Lombok

[<li>Builder</li>](../master/src/main/java/pl/lakomika/gymfit/entity/PasswordReset.java)
[<li>Data</li>](../master/src/main/java/pl/lakomika/gymfit/entity/PasswordReset.java)

### Log4j/JWT

[<li>Log</li>](../master/src/main/java/pl/lakomika/gymfit/configuration/security/jwt/JwtUtils.java)
[<li>JwtUtils</li>](../master/src/main/java/pl/lakomika/gymfit/configuration/security/jwt/JwtUtils.java)

### Spring Boot

<b>REST API</b>
[<li>Controller</li>](../master/src/main/java/pl/lakomika/gymfit/controllers/TaxController.java)
[<li>Service</li>](../master/src/main/java/pl/lakomika/gymfit/services/InvoiceService.java)

### Spring Data JPA

[<li>Repository</li>](../master/src/main/java/pl/lakomika/gymfit/repository/InvoiceRepository.java)
[<li>Entity</li>](../master/src/main/java/pl/lakomika/gymfit/entity/Client.java)
[<li>DTO</li>](../master/src/main/java/pl/lakomika/gymfit/DTO/invoice/InvoiceCreateClientDataRequest.java)

### Spring Security

[<li>SecurityConfiguration</li>](../master/src/main/java/pl/lakomika/gymfit/configuration/security/SecurityConfiguration.java)

### PostgreSQL

[<li>Repository</li>](../master/src/main/java/pl/lakomika/gymfit/repository/InvoiceRepository.java)

### Model Mapper

[<li>Object to DTO</li>](../master/src/main/java/pl/lakomika/gymfit/DTO/invoice/InvoicesPendingResponse.java)
