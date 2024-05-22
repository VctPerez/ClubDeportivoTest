import http from "k6/http"
import { check, sleep } from "k6"

export const options = {
    vus : 5,
    duration : "1m",
    thresholds: {
        http_req_failed : [{
        threshold: 'rate==0',
        abortOnFail: true,
        }],
        http_req_duration: ['avg<100']
    }
}

export default () =>{
    const urlRes = http.get('http://localhost:8080/medico/1');
    
    check(urlRes, {"El codigo de respuesta fue 200: ": () => urlRes.status == 200})
    
    sleep(1);
}

// /\      |‾‾| /‾‾/   /‾‾/   
// /\  /  \     |  |/  /   /  /    
// /  \/    \    |     (   /   ‾‾\  
// /          \   |  |\  \ |  (‾)  | 
// / __________ \  |__| \__\ \_____/ .io

// execution: local
//    script: smoke_test.js
//    output: -

// scenarios: (100.00%) 1 scenario, 5 max VUs, 1m30s max duration (incl. graceful stop):
//          * default: 5 looping VUs for 1m0s (gracefulStop: 30s)


// ✓ El codigo de respuesta fue 200: 

// checks.........................: 100.00% ✓ 300      ✗ 0  
// data_received..................: 85 kB   1.4 kB/s
// data_sent......................: 26 kB   438 B/s
// http_req_blocked...............: avg=10.62µs min=1.95µs  med=6.63µs   max=254.59µs p(90)=9.62µs   p(95)=11.59µs
// http_req_connecting............: avg=1.8µs   min=0s      med=0s       max=118.74µs p(90)=0s       p(95)=0s     
// ✓ http_req_duration..............: avg=3.7ms   min=1.12ms  med=3.88ms   max=6.54ms   p(90)=5.18ms   p(95)=5.44ms 
//   { expected_response:true }...: avg=3.7ms   min=1.12ms  med=3.88ms   max=6.54ms   p(90)=5.18ms   p(95)=5.44ms 
// ✓ http_req_failed................: 0.00%   ✓ 0        ✗ 300
// http_req_receiving.............: avg=356.6µs min=18.87µs med=349.95µs max=1.17ms   p(90)=663.99µs p(95)=770µs  
// http_req_sending...............: avg=28.21µs min=6.42µs  med=26.34µs  max=125.01µs p(90)=39.42µs  p(95)=46.73µs
// http_req_tls_handshaking.......: avg=0s      min=0s      med=0s       max=0s       p(90)=0s       p(95)=0s     
// http_req_waiting...............: avg=3.32ms  min=1ms     med=3.43ms   max=6.14ms   p(90)=4.64ms   p(95)=4.86ms 
// http_reqs......................: 300     4.977304/s
// iteration_duration.............: avg=1s      min=1s      med=1s       max=1s       p(90)=1s       p(95)=1s     
// iterations.....................: 300     4.977304/s
// vus............................: 5       min=5      max=5
// vus_max........................: 5       min=5      max=5
