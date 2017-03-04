const net = require('net');
const client = net.connect({port: 17429}, () => {
	// 'connect' listener
	console.log('connected to server!');
	client.write('b b\n');
	client.write('STATUS\n');
});
client.on('data', (data) => {
	console.log(data.toString());
	client.end();
});
client.on('end', () => {
	console.log('disconnected from server');
});

client.on('error', (err) =>{
	console.log(err);
});

process.stdin.on('readable', () => {
  var chunk = process.stdin.read();
  console.log("chunk: ", chunk);
});