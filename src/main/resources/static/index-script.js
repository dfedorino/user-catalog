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
            return createTableDataRow(userData);
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