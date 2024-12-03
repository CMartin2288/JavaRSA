# RSA Implementation in Java Programs

This repo contains multiple files for running and testing the RSA encryption method in Java.

## Compilation

Compile the program by running the following commands in the directory the .java files are in
(make sure that RSA.java is also in the directory)

> javac RSAServer.java
>
> javac RSAClient.java

## Running

Run the code by running the following commands in the same directory as the compiled code

> java RSAServer
>
> java RSAClient <Hostname> <Port Number>

If you are running both programs on the same machine, the Hostname will be localhost.
The Port Number is given by the Server program and is automatically assigned

## RSA

RSA.java contains all of the helper functions for both the Server and Client

## RSAServer

The RSA Server is fully automated. It receives the encrypted message from the client and returns it in plain text.
It is set up with a 30 second timeout if no clients connect.
Multiple clients can connect without restarting the server, but only one at a time.

## RSAClient

The RSA Client takes in numbers from the user, encrypts them, and sends them to the server.
Any positive number under 1,000,000 is safe to send, but higher numbers may be above N depending on the generated primes.
There are no safeguards for user input, anything besides a number or Q will cause crashes.
Entering Q instead of a number will close the client and allow the server to accept a new client.

Current implementation requires command line arguments, inform me and I can change it to use runtime input instead.

## RSAUnitTest

This is the set of Unit Tests to prove the functionality of the helper functions.
Tests can be run in VSCode using the Test Runner for Java extension.
This extension is included in the Extension Pack for Java.

Most tests use java's BigInteger functions or brute force checking for comparison.

Some tests use random number generation to prove reliability across varied input.
These tests can be run multiple times and should never fail.