'use-strict';

const request = require('request');

try {
    for (let i = 0; i < 20; i++) {
        request.get("http://localhost:8080/api/v1/items/61", function (error, response, body) {
            if (error) {
                console.error('error:', error);
            }
            if (response) {
                console.log('statusCode:', response && response.statusCode);
            }
            if (body) {
                console.log('body:', body);
            }
        })
    }
} catch (err) {
    console.error(err);
}
