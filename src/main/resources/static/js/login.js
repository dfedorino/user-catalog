const login = document.getElementById('username');
const password = document.getElementById('password');
const loginButton = document.getElementById('loginbtn');

loginButton.onclick = () => {
    let loginValue = login.value;
    let passwordValue = password.value;
    let user = {
        login: loginValue,
        password: passwordValue,
    }
    sendRequest('POST', 'text', 'http://localhost:8080/login', user)
        .then(d => {
            let parsedData = JSON.parse(d);
            let jwt = parsedData.access_token;
            localStorage.setItem(loginValue, jwt);
        })
        .catch(e => console.log(e));
}

function sendRequest(method, type, url, body = null) {
    return new Promise((resolve, reject) => {
        const xhr = new XMLHttpRequest();
        xhr.open(method, url);
        xhr.responseType = type;
        xhr.setRequestHeader('content-type', 'application/json');
        xhr.onload = () => {
            let json = xhr.response;
            if (xhr.status >= 400) {
                reject(xhr.response);
            } else {
                resolve(json);
                window.location.replace("http://localhost:8080");
            }
        }
        xhr.onerror = () => {
            reject(xhr.response);
        }
        xhr.send(JSON.stringify(body));
    });
}