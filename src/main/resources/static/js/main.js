var messageApi = Vue.resource("/messages{/id}");

function getIndex(list, id) {
    for (var i = 0; i < list.length; i++) {
        if (list[i].id === id) {
            return i;
        }
    }

    return -1;
}

Vue.component('message-form', {
    props: ['messages', 'messageAttr'],
    data: function () {
        return {
            message: '',
            id: ''
        }
    },
    watch: {
        messageAttr: function (newValue, oldValue) {
            this.message = newValue.message;
            this.id = newValue.id;
        }
    },
    template:
        '<div>' +
        '<input type = "text" placeholder = "Input your message" v-model = "message" />' +
        '<input type = "button" value = "Submit" @click = "save" />' +
        '</div>',
    methods: {
        save: function () {
            var message = {message: this.message};

            if (this.id) {
                messageApi.update({id: this.id}, message).then(result =>
                    result.json().then(data => {
                        var index = getIndex(this.messages, data.id);
                        this.messages.splice(index, 1, data);
                        this.message = '';
                        this.id = ''
                    }))
            } else {
                messageApi.save({}, message).then(result =>
                    result.json().then(data => {
                        this.messages.push(data);
                        this.message = ''
                    }))
            }

        }
    }
});

Vue.component('message-row', {
    props: ['message', 'editMessage', 'messages'],
    template:
        '<div>' +
        '<i>({{message.id}})</i> {{message.message}}' +
        '<span style="position: absolute; right: 0">' +
        '<input type = "button" value="Edit" @click="edit"/>' +
        '<input type = "button" value="Delete" @click="del"/>' +
        '</span>' +
        '</div>',
    methods: {
        edit: function () {
            this.editMessage(this.message);
        },
        del: function () {
            messageApi.remove({id: this.message.id}).then(result => {
                if (result.ok) {
                    this.messages.splice(this.messages.indexOf(this.message, 1))
                }
            })
        }
    }
});

Vue.component('messages-list', {
    props: ['messages'],
    data: function () {
        return {
            message: null
        }
    },
    template:
        '<div style="position: relative; width: 300px;">' +
        '<message-form :messages = "messages" :messageAttr = "message"/>' +
        '<message-row v-for="message in messages"' +
        ' :key="message.id"' +
        ' :message="message"' +
        ' :editMessage = "editMessage"' +
        ' :messages = "messages"/>' +
        '</div>',
    methods: {
        editMessage: function (message) {
            this.message = message;
        }
    }
});

var app = new Vue({
    el: '#app',
    template:
        '<div>' +
        '<div v-if="!profile">You need to login using <a href="/login">Google</a></div>' +
        '<div v-else>' +
        '<div>{{profile.name}}&nbsp;<a href="/logout">Logout</a></div>' +
        '<messages-list :messages = "messages" />' +
        '</div>' +
        '</div>',
    data: {
        messages: frontendData.messages,
        profile: frontendData.profile
    }
});