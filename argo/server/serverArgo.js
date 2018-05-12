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
    res.header('content-type', 'json');
    database.collection("Eventi").find({}).toArray(function(err,result){
        if(err) throw err;
        console.log(result);
        res.send(result);
        res.end();
    })
}

function Login(req,res)
{
    console.log("login");
    var username = req.body.Username
    var psw = req.body.Password
    database.collection("Utenti").findOne({name:username},
    function(err,result){
        if(err)
        {
            res.send("UserNotExist")
            console.log("Sent User Not Exist")
        }
        else
        {
            if(result.password)
        }
    })
}

server.use(restify.plugins.bodyParser());
server.listen(ServerConfiguration.Port,ServerConfiguration.Ip,ServerCallBack)

MongoClient.connect("mongodb://localhost:27017/",MongoCallBack);
server.get('/Eventi',getEventi)
server.post('/Login',Login)