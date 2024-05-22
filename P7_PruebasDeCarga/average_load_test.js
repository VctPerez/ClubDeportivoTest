import http from "k6/http"
import { sleep } from "k6"

export const options = {
    stages: [
        {duration: '5m', target: 1500},
        {duration: '20m', target: 1500},
        {duration: '5m', target: 0}
    ],
}

export default () =>{
    const urlRes = http.get('http://localhost:8080/medico/1');
    sleep(1);
}