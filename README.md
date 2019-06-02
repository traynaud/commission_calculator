# commission_calculator
Compute the commission of freelancers based on generic parameters

## Instructions

To use this Application:

1. Ensure that you have a server MySQL running (If not installed, you can dowload it from the [Official Web site](https://dev.mysql.com/downloads/mysql/)

*Note: You can use another SQL server, but you must update the <code>pom.xml</code> and the <code>application.properties</code> files of the application*

2. Clone the repository: 
```
git clone https://github.com/traynaud/commission_calculator.git
```
3. Copy the <code>src/main/resources/application.properties.conf</code> as a local Version of <code>application.properties</code>
4. Fill the empty fields with your database credentials
5. Create an account on https://ipstack.com and get your personal token
6. Report your token in <code>application.properties</code>
7. Export the application as Runnable packaged Jar. It includes its own apache Server
8. Launch the jar:  
```
java -jar application.jar
```

## APIs

Once you Application is started, the list of available APIs are given by the link: http://localhost:8080/swagger-ui.html

### Description:

* *Compute the freelancer commission based on available parameters:*
```
POST /commission/compute
```

* *Add a new configuration rule to compute freelancers commission:*
```
POST /rules/add
```

* *List all existing rules that are used to compute freelancer commissions:*
```
GET /rules/list
```

## Insomnia

To test the APIs, you can operate from the swagger-ui page, or from any client of your choice.
The application comes with a Insomnia configuration file.

To use Insomnia, process as follow:
1. Download Insomnia from the [Insomnia official Website](https://insomnia.rest/download/)
2. Import a configuration file from <code>src/main/resources/insomnia</code> (The lattest the better)
3. <Optional> Change the value of the URL if you are using another configuration than <code>localhost:8080</code>
  
