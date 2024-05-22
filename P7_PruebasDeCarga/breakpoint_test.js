import http from "k6/http";
import { sleep, check } from "k6";

export const options = {  // Quitando las lineas comentadas se hace el test con executor y sin ellas se hace el test sin executor
//   scenarios: { 
//     breakpoint: {
//       executor: "ramping-arrival-rate", // Incrementa la carga exponencial
//       preAllocatedVUs: 1000, //VUs alocados inicialmente
//       maxVUs: 1e7, //VUs maximo
      stages: [
        { duration: "10m", target: 100000 }
      ],
//     },
//   },
  thresholds: {
    http_req_failed: [
      {
        threshold: "rate<=0.01",
        abortOnFail: true,
      },
    ],
  },
};

export default () =>{
    const urlRes = http.get('http://localhost:8080/medico/1');

    check(urlRes, {"El codigo de respuesta fue 200: ": () => urlRes.status == 200})
    
    sleep(1);
}



// CON EXECUTOR


// ✗ El codigo de respuesta fue 200: 
// ↳  98% — ✓ 377102 / ✗ 4606

// checks.........................: 98.79% ✓ 377102      ✗ 4606   
// data_received..................: 107 MB 1.1 MB/s
// data_sent......................: 34 MB  363 kB/s
// dropped_iterations.............: 298245 3210.11262/s
// http_req_blocked...............: avg=12.79µs  min=0s      med=3.07µs   max=383.83ms p(90)=4.77µs p(95)=7.72µs  
// http_req_connecting............: avg=5.34µs   min=0s      med=0s       max=20.98ms  p(90)=0s     p(95)=0s      
// http_req_duration..............: avg=405.36ms min=0s      med=122.17ms max=4.02s    p(90)=1.28s  p(95)=1.69s   
//  { expected_response:true }...: avg=410.7ms  min=336.9µs med=128.87ms max=4.02s    p(90)=1.29s  p(95)=1.7s    
// ✗ http_req_failed................: 1.30%  ✓ 4978        ✗ 377841 
// http_req_receiving.............: avg=1.72ms   min=0s      med=58.53µs  max=545.08ms p(90)=3.04ms p(95)=9.47ms  
// http_req_sending...............: avg=59.68µs  min=0s      med=12.7µs   max=100.55ms p(90)=75.4µs p(95)=179.33µs
// http_req_tls_handshaking.......: avg=0s       min=0s      med=0s       max=0s       p(90)=0s     p(95)=0s      
// http_req_waiting...............: avg=403.58ms min=0s      med=119.97ms max=4.02s    p(90)=1.28s  p(95)=1.69s   
// http_reqs......................: 382819 4120.411418/s
// iteration_duration.............: avg=1.75s    min=1s      med=1.13s    max=31.78s   p(90)=2.35s  p(95)=2.72s   
// iterations.....................: 380153 4091.716351/s
// vus............................: 27632  min=68        max=27632
// vus_max........................: 27633  min=1000      max=27633


// running (01m32.9s), 00000000/00027635 VUs, 378053 complete and 27633 interrupted iterations
// breakpoint ✗ [====>---------------------------------] 00027511/00027634 VUs  01m32.7s/10m0s  015317.92 iters/s
// ERRO[0094] thresholds on metrics 'http_req_failed' were crossed; at least one has abortOnFail enabled, stopping test prematurely



// SIN EXECUTOR (MAX VUs = 12970)


// ✗ El codigo de respuesta fue 200: 
// ↳  99% — ✓ 340814 / ✗ 273
// 
// checks.........................: 99.91% ✓ 340814      ✗ 273     
// data_received..................: 96 MB  634 kB/s
// data_sent......................: 31 MB  202 kB/s
// http_req_blocked...............: avg=88.35µs  min=0s       med=3.1µs    max=2.24s    p(90)=5.43µs   p(95)=11.92µs 
// http_req_connecting............: avg=17.83µs  min=0s       med=0s       max=121.66ms p(90)=0s       p(95)=0s      
// http_req_duration..............: avg=382.87ms min=0s       med=210.68ms max=1m2s     p(90)=669.16ms p(95)=802.23ms
//  { expected_response:true }...: avg=369.78ms min=374.08µs med=216.61ms max=31.23s   p(90)=670.87ms p(95)=803.81ms
// ✗ http_req_failed................: 1.25%  ✓ 4330        ✗ 340815  
// http_req_receiving.............: avg=2.53ms   min=0s       med=67.39µs  max=9.09s    p(90)=2.48ms   p(95)=7.06ms  
// http_req_sending...............: avg=538.57µs min=0s       med=12.69µs  max=14.84s   p(90)=40.89µs  p(95)=126.13µs
// http_req_tls_handshaking.......: avg=0s       min=0s       med=0s       max=0s       p(90)=0s       p(95)=0s      
// http_req_waiting...............: avg=379.79ms min=0s       med=208.75ms max=1m2s     p(90)=666.49ms p(95)=797.96ms
// http_reqs......................: 345145 2277.947968/s
// iteration_duration.............: avg=1.72s    min=1s       med=1.22s    max=1m3s     p(90)=1.69s    p(95)=1.94s   
// iterations.....................: 340454 2246.987491/s
// vus............................: 12970  min=0         max=12970 
// vus_max........................: 100000 min=13021     max=100000
// 
// 
// running (02m31.5s), 000000/100000 VUs, 340447 complete and 12981 interrupted iterations
// default ✗ [======>-------------------------------] 005684/100000 VUs  01m54.5s/10m00.0s
// ERRO[0161] thresholds on metrics 'http_req_failed' were crossed; at least one has abortOnFail enabled, stopping test prematurel