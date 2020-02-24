// https://vuetifyjs.com/ko/customization/theme#%EB%B0%9D%EA%B2%8C-%EC%96%B4%EB%91%A1%EA%B2%8C-light-and-dark
var vuetify = new Vuetify({
    theme: { dark: true }
})

// https://www.npmjs.com/package/http-vue-loader
// https://medium.com/@jamesweee/using-vue-js-single-file-component-without-module-bundlers-aea58d892ad9
// - Custom Component

var app = new Vue({
    el: '#app',
    // vuetify: new Vuetify(),
    vuetify: vuetify,
    data: {
        drawer: true,
        items: {
            'Applications': [
                { title: 'CI/CD', icon: 'mdi-image' },
                { title: 'xxx', icon: 'mdi-help-box' }
            ],
            'Administrator': [
                { title: 'Realms', icon: 'mdi-image' },
                { title: 'Projects', icon: 'mdi-help-box' }
            ]
        },
        selected: 'Realms',
        miniVariant: false
    },
    components: {
        'realms': httpVueLoader('components/realms.vue'),
        'projects': httpVueLoader('components/projects.vue')
    }
})