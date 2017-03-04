const net = require('net');
const client = net.connect({port: 17429}, () => {
	// 'connect' listener
	console.log('connected to server!');

	process.stdin.setEncoding('utf8');
	process.stdin.on('readable', () => {
		var chunk = process.stdin.read();
		if(chunk !== null){
			console.log('SENT: {', chunk, '}');
			client.write(chunk);
		}
	});
});
client.on('data', (data) => {
	console.log('RECEIVED: {', data.toString(), '}');
});
client.on('end', () => {
	console.log('disconnected from server');
});

client.on('error', (err) =>{
	console.log(err);
});
