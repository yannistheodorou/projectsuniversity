0. Download the music from URL and paste its content in datasource folder
1. Open InteliJ IDEA
2. Import project
3. Load libraries (File>>Project structure>>Libraries) from lib folder
4. Configure how many publisher and brokers the system want to have(SystemController class).
5. Configure SystemController ip (in Constants class).
6. First start SystemController*
7. Start publishers and brokers
8. Look at SystemControler terminal to see the connected brokers and publishers.
9. Run consumer class which has a simple menu DEMO.
10. Have fun!
 
 
*Run each publisher/broker on a seperate process. When creating a publisher/broker do not forget to pass appropriate command line arguments.
 
Explenation command line arguments:
Broker -> (port_number)
publisher -> (port_number) (datasource_index)
 
If something went wrong please contact us so that we can help you resolve the problem!