import http from 'k6/http';
import { check, sleep } from 'k6';

const maxVU = 10000;

export const options = {
    stages: [
    { duration: '3m', target: maxVU * 0.8 },
    { duration: '3m', target: maxVU * 0.8 },
    { duration: '2m', target: 0 },
    ],
    thresholds: {
        http_req_failed: [{
            threshold: 'rate <= 0.01',
            abortOnFail: true,
        }],
        http_req_duration: [{
            threshold: 'avg < 1000',
            abortOnFail: true,
        }]
    }

};

export default function () {
    let res = http.get('http://localhost:8080/medico/1');
    check(res, { 'status was 200': (r) => r.status == 200 });
    sleep(1);
}