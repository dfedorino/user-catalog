const form = document.getElementById('form');
const username = document.getElementById('username');
const password = document.getElementById('password');
const loginbtn = document.getElementById('loginbtn');

loginbtn.addEventListener('click', (e) => {
    e.preventDefault();
    let loginData = {
        username: username.value.trim(),
        password: password.value.trim(),
    }
    checkInputs(loginData);
    let inputs = [username, password];
    if (allFieldsHaveSuccess(inputs)) {
        sendPostRequest(loginData);
    }
});

function checkInputs(loginData) {
    if (loginData.username === '') {
        addClassAndMessage(username, 'form-control error', 'Username cannot be empty');
    } else {
        addClassAndMessage(username, 'form-control success', '');
    }

    if (loginData.password === '') {
        addClassAndMessage(password, 'form-control error', 'Password cannot be empty');
    } else {
        addClassAndMessage(password, 'form-control success', '');
    }
}

function addClassAndMessage(input, className, message) {
    const formControl = input.parentElement;
    formControl.className = className;
    const small = formControl.querySelector('small');
    small.innerText = message;
}

function allFieldsHaveSuccess(inputs) {
    for (let i = 0; i < inputs.length; i++) {
        if (inputs[i].parentElement.classList.contains('error')) {
            return false;
        }
    }
    return true;
}

function sendPostRequest(newUser) {
    let loginAndPassword = {
        login: newUser.username,
        password: newUser.password,
    }
    sendRequest('POST', 'json', 'http://localhost:8080/login', loginAndPassword)
                .then(d => {
                    console.log(d);
                    localStorage.setItem(loginAndPassword.login, d.access_token);
                    window.location.replace("http://localhost:8080");
                })
                .catch(e => {
                    console.log(e);
                    addClassAndMessage(loginbtn, 'form-control error', 'Login failed');
                });
}

function sendRequest(method, responseType, url, body = null) {
    return new Promise((resolve, reject) => {
        console.log('>> about to send request with user -> ' + JSON.stringify(body))
        const xhr = new XMLHttpRequest();
        xhr.open(method, url);
        xhr.responseType = responseType;
        xhr.setRequestHeader('content-type', 'application/json');
        xhr.onload = () => {
            let json = xhr.response;
            if (xhr.status >= 400) {
                reject(xhr.response);
            } else {
                resolve(json);
            }
        }
        xhr.onerror = () => {
            reject(xhr.response);
        }
        xhr.send(JSON.stringify(body));
    });
}