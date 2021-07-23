const showAllUsersButton = document.querySelector(".showallusersbtn");
        const usersField = document.getElementById("usersField");

        const loginField = document.querySelector(".login");
        const showCurrentUserButton = document.querySelector(".showcurrentuserbtn");
        const userField = document.getElementById("userField");

        showAllUsersButton.onclick = () => {
            // let user = {
            //     login: "login",
            //     email: "email",
            //     phoneNumber: "phone",
            //     street: "street",
            //     zipCode: "zip"
            // };
            // let user1 = {
            //     login: "login1",
            //     email: "email1",
            //     phoneNumber: "phone1",
            //     street: "street1",
            //     zipCode: "zip1"
            // };
            // let ua = [user, user1];
            // createTable(ua);
            usersField.innerHTML = '';
            const xhr = new XMLHttpRequest();
            xhr.open('GET', 'http://localhost:8080/users');
            xhr.responseType = 'json';
            xhr.onload = () => {
                let userArray = xhr.response;
                createTable(userArray);           
            }

            xhr.onerror = () => {
                usersField.innerHTML = "Server didn't respond";
            }
            xhr.send();
        }

        showCurrentUserButton.onclick = () => {
            const xhr = new XMLHttpRequest();
            let login = loginField.value;
            let url = 'http://localhost:8080/users/' + login;
            xhr.open('GET', url);
            console.log(localStorage);
            let jwt = localStorage.getItem(login);
            xhr.setRequestHeader('Authorization', 'Bearer ' + jwt);
            xhr.responseType = 'json';
            xhr.onload = () => {
                let user = xhr.response;
                userField.textContent = user.login;
            }
            xhr.send();
        }

        function createTable (userArray) {
            // clear previous table
            usersField.innerHTML = '';
            // table creation
            let table = document.createElement('table');
            let thead = document.createElement('thead');
            let tbody = document.createElement('tbody');

            table.appendChild(thead);
            table.appendChild(tbody);

            // Adding the entire table to the body tag
            document.getElementById('usersField').appendChild(table);

            // Creating and adding data to first row of the table
            let headings = ['Login', 'Email', 'Phone', 'Street', 'Zip Code'];
            let headingRow = createHeadingRow(headings);
            thead.appendChild(headingRow);

            userArray.forEach(user => {
                // Creating and adding data to second row of the table
                let rowWithUserData = createUserDataRow(user);
                tbody.appendChild(rowWithUserData); 
            });
        }

        function createHeadingRow(names) {
            let row = document.createElement('tr');
            names.forEach(name => {
                let heading = createHeading(name);
                row.appendChild(heading);
            });
            return row;
        }

        function createTableDataRow(data) {
            let row = document.createElement('tr');
            data.forEach(datum => {
                let cell = createTableData(datum);
                row.appendChild(cell);
            });
            return row;
        }

        function createUserDataRow(user) {
            let userData = [user.login, user.email, user.phoneNumber, user.street, user.zipCode];
            let tableRow = createTableDataRow(userData);
            tableRow.setAttribute('contenteditable', true);
            tableRow.addEventListener('click', (ev) => {
                if (!inEditing(tableRow)) {
                    startEditingRow(tableRow);
                }
            });
            return tableRow;
        }

        function startEditingRow(tr) {
            tr.className = 'in-editing';
            let btnTd = document.createElement('td');
            btnTd.setAttribute('contenteditable', false);
            let saveBtn = document.createElement('button');
            saveBtn.innerHTML = 'Save';
            saveBtn.addEventListener('click', (ev) => {
                ev.stopPropagation();
                finishEditingRow(tr);
            })
            let cancelBtn = document.createElement('button');
            cancelBtn.innerHTML = 'Cancel';
            cancelBtn.addEventListener('click', (ev) => {
                cancelEditingRow(tr);
            })
            btnTd.appendChild(saveBtn);
            btnTd.appendChild(cancelBtn);
            tr.append(btnTd);
        }

        function finishEditingRow(userDataRow) {
            let dataArray = userDataRow.cells;
            let userLogin = dataArray[0].innerHTML;
            let userEmail = dataArray[1].innerHTML;
            let userPhone = dataArray[2].innerHTML;
            let userStreet = dataArray[3].innerHTML;
            let userZip = dataArray[4].innerHTML;
            let updatedUser = {
                login: userLogin,
                email: userEmail,
                phoneNumber: userPhone,
                street: userStreet,
                zipCode: userZip
            }
            console.log('>> send updated user -> ' + updatedUser);
            // sent xhr put request
            const xhr = new XMLHttpRequest();
            xhr.open('PUT', 'http://localhost:8080/users/' + userLogin);
            xhr.responseType = 'json';
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem(userLogin));
            xhr.setRequestHeader('Content-Type', 'application/json')
            xhr.onload = () => {
                console.log(xhr.response);           
            }
            xhr.send(JSON.stringify(updatedUser));
            userDataRow.deleteCell(userDataRow.cells.length - 1);
            userDataRow.classList.remove('in-editing');
            // reload the table
        }

        function inEditing(tr) {
            return tr.classList.contains('in-editing');
        }

        function createHeading(name) {
            let heading = document.createElement('th');
            heading.innerHTML = name;
            return heading;
        }

        function createTableData(data) {
            let tableData = document.createElement('td');
            tableData.innerHTML = data;
            return tableData;
        }