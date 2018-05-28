import axios from 'axios';
const BASE_URL = "http://localhost:8080";
const API_URL = BASE_URL + "/document/upload/";

export const uploadNewFile = (selectedFile) => {
    const formData = new FormData();
    formData.append('file', selectedFile)
    axios.post(API_URL, formData)
        .then(res => {
            console.log(res);
        })
        .catch(function (error) {
            console.log(error);
        });
}