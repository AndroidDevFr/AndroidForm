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

# TODO

- List all form components
- Customize each component
- Generate Views from Sections / Row
- Write Doc
- Complete the README

Credits
-------

This project is an open source initiative of the Slack French Community **AndroidDevFr**

To join this slack (french speakers only) : http://bit.ly/AndroidDevFr

License
--------

    Copyright 2017 AndroidDevFr, Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
