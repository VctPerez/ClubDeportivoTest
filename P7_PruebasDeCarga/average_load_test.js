import http from "k6/http"
import { sleep, check } from "k6"

let maxVUs = 10000

export const options = {
    stages: [
        {duration: '3m', target: maxVUs/2},
        {duration: '3m', target: maxVUs/2},
        {duration: '2m', target: 0}
    ],
    thresholds: {
        http_req_failed: [
          {
            threshold: "rate<=0.01",
            abortOnFail: true,
          },
        ],
      },
}

export default () =>{
    const urlRes = http.get('http://localhost:8080/medico/1');
    check(urlRes, {
        "status is 200": (r) => r.status === 200,
    });
    sleep(1);
}