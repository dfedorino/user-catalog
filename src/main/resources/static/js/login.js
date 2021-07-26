const login = document.querySelector('.login');
        const password = document.querySelector('.psw');

        const loginButton = document.querySelector('.loginbtn');

        const loginStatus = document.getElementById("login-status");

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
                        loginStatus.innerHTML = '';
                        loginStatus.innerHTML = 'Login Failed!';
                    } else {
                        resolve(json);
                        loginStatus.innerHTML = '';
                        loginStatus.innerHTML = 'Login Successful!';
                        window.location.replace("http://localhost:8080");
                    }
                }
                xhr.onerror = () => {
                    reject(xhr.response);
                    loginStatus.innerHTML = '';
                    loginStatus.innerHTML = 'Login Failed!';
                }
                xhr.send(JSON.stringify(body));
            });
        }