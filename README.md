# AndroidForm

Generates a form for Android

**WORK IN PROGRESS**

# Create a form

```kotlin
form.create {

    section("Section1") {
        id = 0
        textRow {
            id = 2
            title = "Text Row"
            placeholder = "Enter text here"
            validator = { s ->
                s != null && s.length > 3
            }

            //listeners
            addValueChangeListener<TextRow> { textRow, value ->
                //change background following value, for example
                //textRow.view?.background = ...
            }
            addOnViewCreatedListener<TextRow> { textRow ->

            }
        }
        phoneRow {
            id = 3
            title = "Phone Row"
            placeholder = "020202020202"
        }
    }
    section("Section2") {
        id = 1
        dateRow {
            id = 5
            title = "Text Row"
            value = Date()
        }
    }

}
```

# Fetch values

`Map<Int, Any?>`

```kotlin
val values = form.values()
```

# Fetch single value

```kotlin
val row = form.rowById(5) as DateRow?
val value = row?.value
```

# Want to contribute

Check the Github project : https://github.com/AndroidDevFr/AndroidForm/projects/1

Follow the guidelines :

1) Everything has to be overridable directly using lambdas, eg: `onViewCreated`, avoid if possible abstract methods

2) The code has to be written in English (of course)

3) Please make a Pull Request for each feature, including a sample code

4) Have Fun doing some Kotlin :)


# Credits

This project is an open source initiative of the Slack French Community **AndroidDevFr**

To join this slack (french speakers only) : http://bit.ly/AndroidDevFr

# Inspiration

This project is inspired from a swift open source library Eureka, by **xmartlabs**
https://github.com/xmartlabs/Eureka

# License

    Copyright 2018 AndroidDevFr, Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
