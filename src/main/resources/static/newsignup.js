const form = document.getElementById('form');
const username = document.getElementById('username');
const email = document.getElementById('email');
const phone = document.getElementById('phone');
const address = document.getElementById('address');
const zip = document.getElementById('zip');
const password = document.getElementById('password');
const passwordCheck = document.getElementById('password_check');

form.addEventListener('submit', (e) => {
    e.preventDefault();

    checkInputs();
});

function checkInputs() {
    const usernameValue = username.value.trim();
    const emailValue = email.value.trim();
    const phoneValue = phone.value.trim();
    const addressValue = address.value.trim();
    const zipValue = zip.value.trim();
    const passwordValue = password.value.trim();
    const passwordCheckValue = passwordCheck.value.trim();

    if (usernameValue === '') {
        addClassAndMessage(username, 'form-control error', 'Username cannot be empty');
    } else {
        addClassAndMessage(username, 'form-control success', '');
    }

    if (emailValue === '') {
        addClassAndMessage(email, 'form-control error', 'Email cannot be empty');
    } else if (!isValidEmail(emailValue)) {
        addClassAndMessage(email, 'form-control error', 'Not a valid email');
    } else {
        addClassAndMessage(email, 'form-control success', '');
    }

    if (phoneValue === '') {
        addClassAndMessage(phone, 'form-control error', 'Phone cannot be empty');
    } else {
        addClassAndMessage(phone, 'form-control success', '');
    }

    if (addressValue === '') {
        addClassAndMessage(address, 'form-control error', 'Address cannot be empty');
    } else {
        addClassAndMessage(address, 'form-control success', '');
    }

    if (zipValue === '') {
        addClassAndMessage(zip, 'form-control error', 'Zip code cannot be empty');
    } else {
        addClassAndMessage(zip, 'form-control success', '');
    }

    if (passwordValue === '') {
        addClassAndMessage(password, 'form-control error', 'Password cannot be empty');
    } else if (!isValidPassword(passwordValue)) {
        addClassAndMessage(password, 'form-control error', 'Not a valid password');
    } else {
        addClassAndMessage(password, 'form-control success', '');
    }

    if (passwordCheckValue !== passwordValue) {
        addClassAndMessage(passwordCheck, 'form-control error', 'Passwords do not match');
    } else if(passwordCheckValue === '') {
        // do nothing if the password wasn't entered yet
    } else {
        addClassAndMessage(passwordCheck, 'form-control success', '');
    }
}

function addClassAndMessage(input, className, message) {
    const formControl = input.parentElement;
    formControl.className = className;
    const small = formControl.querySelector('small');
    small.innerText = message;
}

function isValidEmail(email) {
    const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(String(email).toLowerCase());
}

function isValidPassword(password) {
    const re = /^[a-zA-Z0-9!@#$%^&*]{6,16}$/;
    return re.test(String(password));
}