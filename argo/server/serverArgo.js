var restify = require('restify');
var mongodb = require('mongodb');
var MongoClient = require('mongodb').MongoClient;
var bodyParser = require('body-parser');

var server = restify.createServer();

var database;
var ServerConfiguration ={
    Port:8080,
    Ip:"192.168.43.156"
}

function ServerCallBack(err)
{
    if(err) throw err
    console.log("Server Online")
}

function MongoCallBack(err,db)
{
    if(err)throw err
    database=db.db("Progetto")
    console.log("Connesso con successo al database Progetto")
}

function getEventi(req,res)
{
    console.log("getEventi")
    database.collection("Eventi").find().toArray(function(err,result)
    {
        if(err) throw err;
        res.send(result);
        console.log("Sent eventi");
        res.end();
    })
}

function Login(req,res)
{
    console.log("login");
    var username = req.body.name;
    var psw = req.body.password;
    var message;
    database.collection("Utenti").findOne({name:username},
    function(err,result)
    {
        if(err)
        throw err

        if(result)
        {
            if(result.password==psw)
            {
                message="LoginSucces";
                res.send(message);
                console.log("Sent "+message);
                res.end();
            }
            else
            {
                message="WrongPassword";
                res.send(message);
                console.log("Sent "+message);
                res.end();
            }
        }
        else
        {
            message="UserNotExist";
            res.send(message);
            console.log("Sent "+message);
            res.end();
        }
    });
}

function Singup(req,res)
{
    console.log("Singup");
    var username = req.body.name;
    var psw = req.body.password;
    database.collection("Utenti").findOne({name:username},
        function(err,result) 
        {
            console.log("chiamato il db")
            if(err) throw err;

            if(result)
            {
                var message="UserAlreadyExists";
                res.send(message);
                console.log("Sent "+message);
                res.end();
            }
            else
            {
                database.collection("Utenti").insertOne({name:username, password:psw},
                    function(err)
                    {
                        if(err) throw err;
                        var message="UserAddedSuccessfully";
                        res.send(message);
                        console.log("Sent "+message);
                        res.end();
                    }
                )
            }
        });
}

function DeleteAccount(req,res)
{
    console.log("DeleteAccount");
    var username=req.params
    console.log(username);
    res.send("Error");
    /*database.collection("Utenti").remove({name:username},
    function(err){
        if(err){
            var message="Error"
            res.send(message);
            console.log("Sent "+message);
        }
    })*/
}

server.use(restify.plugins.bodyParser());
server.listen(ServerConfiguration.Port,ServerConfiguration.Ip,ServerCallBack)

MongoClient.connect("mongodb://localhost:27017/",MongoCallBack);
server.get('/Eventi',getEventi)
server.post('/Login',Login)
server.post('/Singup',Singup)
server.del('/DeleteAccount',DeleteAccount)