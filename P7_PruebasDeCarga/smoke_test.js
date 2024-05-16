import http from "k6/http"
import { sleep } from "k6"

export const Options = {
    vus : 5,
    duration : "1m",
    thresholds: {
    http_req_failed : [{
        abortOnFail: true,
    }],
    http_req_duration : "p(100) <= 100"
    },
}

export default () =>{
    const urlRes = http.get('http://localhost:8080/medico/1');
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

// scenarios: (100.00%) 1 scenario, 1 max VUs, 10m30s max duration (incl. graceful stop):
//          * default: 1 iterations for each of 1 VUs (maxDuration: 10m0s, gracefulStop: 30s)


// data_received..............: 4.8 kB  4.6 kB/s
// data_sent..................: 88 B    86 B/s
// http_req_blocked...........: avg=547.06µs min=547.06µs med=547.06µs max=547.06µs p(90)=547.06µs p(95)=547.06µs
// http_req_connecting........: avg=248.9µs  min=248.9µs  med=248.9µs  max=248.9µs  p(90)=248.9µs  p(95)=248.9µs 
// http_req_duration..........: avg=27.54ms  min=27.54ms  med=27.54ms  max=27.54ms  p(90)=27.54ms  p(95)=27.54ms 
// http_req_failed............: 100.00% ✓ 1       ✗ 0  
// http_req_receiving.........: avg=704.05µs min=704.05µs med=704.05µs max=704.05µs p(90)=704.05µs p(95)=704.05µs
// http_req_sending...........: avg=76.82µs  min=76.82µs  med=76.82µs  max=76.82µs  p(90)=76.82µs  p(95)=76.82µs 
// http_req_tls_handshaking...: avg=0s       min=0s       med=0s       max=0s       p(90)=0s       p(95)=0s      
// http_req_waiting...........: avg=26.76ms  min=26.76ms  med=26.76ms  max=26.76ms  p(90)=26.76ms  p(95)=26.76ms 
// http_reqs..................: 1       0.97103/s
// iteration_duration.........: avg=1.02s    min=1.02s    med=1.02s    max=1.02s    p(90)=1.02s    p(95)=1.02s   
// iterations.................: 1       0.97103/s
// vus........................: 1       min=1     max=1
// vus_max....................: 1       min=1     max=1


// running (00m01.0s), 0/1 VUs, 1 complete and 0 interrupted iterations
// default ✓ [======================================] 1 VUs  00m01.0s/10m0s  1/1 iters, 1 per VU