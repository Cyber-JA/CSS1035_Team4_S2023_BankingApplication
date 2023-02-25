
/**
 * This package is used to implement the functionalities of the SJU Bank services.
 * Feel free to use, test and report eventual bugs, errors and vulnerabilities.
 * The documentation tries to be complete and described in depth, showing
 * parameters, return values, exceptions and so on.
 * The secure coding practices are applied into the database.java file, related
 * to a correct and proper management of the database to avoid SQLInjection.
 * Besides it, the usage of different kinds of exceptions such as the InvalidAmountException, helps in avoiding the usage
 * wrong values.
 * Furthermore, Try-with-resource statement is used to free buffer used to store user input.
 * According to numbers overflow vulnerabilities, double values are used within the application.
 * Since double cannot wrap around, but only overflow to infinity and -infinity, the Math.isInfinity method is used before
 * performing some computations.
 * Following the docs in which those secure coding practices are shown.
 * 
 * @see InvalidAmountException
 * 
 * @see database
 * 
 * @see bankDriver
 * 
 * @see Account_S2023_SJUBank
 * 
 */

package bankingAccount;