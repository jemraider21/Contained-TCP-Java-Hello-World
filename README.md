# Contained TCP Hello World

## Description
This is a Java application that receives and sends data over a TCP connection. This Java app will be contained in a Docker image and will have the necessary ports opened. This is to be used with testing out receiving and sending data from an instance of NiFi.

## Needed Software
* Java
* Maven
* Docker
* NiFi (For testing)

## Processor Flow
* Input: GenerateFlowFile -> UpdateAttribute -> PackageFlowFile -> PutTCP
* Output: GetTCP -> UnpackContent

## Processor Configurations
* GenerateFlowFile
    - Properties
        * File Size: 0B
        * Batch Size: 1
        * Data Format: Text
        * Unique FlowFiles: false
        * Custom Text: Hello world
        * Character Set: UTF-8
        * Mime Type: No value set
    - Relationships
        * SUCCESS: to UpdateAttribute
* UpdateAttribute
    - Properties
        * Delete Attributes Expression: No value set
        * Store State: Do not store date
        * Stateful Variables Initial Value: No value set
        * Cache Value Lookup Cache Size: 100
        * document.attribute: Input
    - Relationships
        * SUCCESS: to PackageFlowFile
* PackageFlowFile
    - Properties
        * Maximum Batch Size: 1
    - Relationships
        * ORIGINAL: terminate
        * SUCCESS: to PutTCP
* PutTCP
    - Properties
        * Hostname: localhost
        * Port: 12345
        * Max Size of Socket Send Buffer: 1MB
        * Idle Connection Expiration: 15 seconds
        * Timeout: 10 seconds
        * Connection Per FlowFile: false
        * SSL Context Service: No value set
        * Transmission Strategy: FlowFile-oriented
        * Outgoing Message Delimiter: No value set
        * Character Set: UTF-8
    - Relationships
        * FAILURE: To funnel
        * SUCCESS: terminate
* GetTCP
    - Property
        * Endpoint List: localhost:12346
        * Connection Attempt Count: 3
        * Reconnect interval: 5 sec
        * Receive Buffer Size: 16MB
        * End of message delimiter byte: 13
    - Relationship
        * PARTIAL: Goes to UnpackContent
        * SUCCESS: Goes to UnpackContent
* UnpackContent
    - Packaging Format: flowfile-stream-v3
    - File Filter: .*
    - Password: No value set