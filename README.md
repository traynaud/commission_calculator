# commission_calculator
Compute the commission of freelancers based on generic parameters

To use this Application:

1. Ensure that you have a server MySQL running
2. Clone the repository with <code>git clone https://github.com/traynaud/commission_calculator.git</code>
3. Copy the src/main/resources/application.properties.conf as a local Version of <code>application.properties</code>
4. Fill the empty fields with your database credentials
5. Create an account on https://ipstack.com and get your personal token
6. Report your token in <code>application.properties</code>
7. Export the application as Runnable packaged Jar. It includes its own apache Server
8. Just launch the jar with  <code>java -jar application.jar</code>

APIs

Once you Application is started, the list of available APIs are given by the link: http://localhost:8080/swagger-ui.html

Description:

POST /commission/compute
Compute the freelancer commission based on available parameters

POST /rules/add
Add a new configuration rule to compute freelancers commission

GET /rules/list
List all existing rules that are used to compute freelancer commissions
