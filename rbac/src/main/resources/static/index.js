// https://www.npmjs.com/package/http-vue-loader
// https://medium.com/@jamesweee/using-vue-js-single-file-component-without-module-bundlers-aea58d892ad9
// - Custom Component
var names = ['realms', 'projects', 'config', 'iam']
var comps = names.reduce(function(a, v){ a[v] = httpVueLoader('components/' + v + '.vue'); return a; }, {})
var routes = Object.keys(comps).reduce(function(a, v) { a.push( {path: '/' + v, component: comps[v]} ); return a; }, [])
var router = new VueRouter({ routes: routes })

// https://vuetifyjs.com/ko/customization/theme#%EB%B0%9D%EA%B2%8C-%EC%96%B4%EB%91%A1%EA%B2%8C-light-and-dark
var vuetify = new Vuetify({
    theme: { dark: true }
})

// https://joshua1988.github.io/vue-camp/vue/axios.html#%EC%95%A1%EC%8B%9C%EC%98%A4%EC%8A%A4-%EC%84%A4%EC%B9%98
// axios

// https://vuex.vuejs.org/kr/
// https://vuejs.org/v2/guide/state-management.html#Simple-State-Management-from-Scratch
// https://kr.vuejs.org/v2/guide/components.html#%EB%B9%84-%EB%B6%80%EB%AA%A8-%EC%9E%90%EC%8B%9D%EA%B0%84-%ED%86%B5%EC%8B%A0
var global = {
    realm: {},
    project: {},
    user: {id: '', name: '', role:[]}
}

var app = new Vue({
    el: '#app',
    // vuetify: new Vuetify(),
    router: router,
    vuetify: vuetify,
    data: {
        drawer: true,
        items: {
            // https://vuetifyjs.com/ko/components/lists
            // http://code.meta-platform.com/assets/mdi/preview.html
            'Applications': [
                { title: 'Home', icon: 'mdi-home' },
                { title: 'IAM', icon: 'mdi-account-key', link: '/iam' },
                { title: 'CI/CD', icon: 'mdi-source-pull' },
                { title: 'Settings', icon: 'mdi-book-variant' },
            ],
            'Administrator': [
                { title: 'Realms', icon: 'mdi-sitemap', link: '/realms' },
                { title: 'Projects', icon: 'mdi-presentation', link: '/projects' },
                { title: 'Config', icon: 'mdi-settings', link: '/config' }
            ]
        },
        selected: 'Realms',
        miniVariant: false,
        global: global,
        realms: [],
        projects: []
    },
    components: comps,
    created: function(){
        this.getRealms()
        // this.getProjects()
    },
    wacth: {
        'global.realm': {
            hanlder: function(){
                console.log('global.realm is changed')
                this.getProjects()
            },
            deep: true
        }
    },
    methods: {
        'getRealms': function(){
            var vm = this
            var success = function (res) { vm.realms = res.data; }
            axios.get('/companies').then(success).then(this.selectRealm)
        },
        'getProjects': function(){
            var vm = this
            var success = function (res) { vm.projects = res.data; }
            var url = '/companies/' + (this.global.realm.name || 'dummy') + '/projects'

            this.global.project = {}
            axios.get(url).then(success).then(this.selectProject)
        },
        'selectRealm': function(){
            if(!this.global.realm.name){
                this.global.realm = this.realms[0]
                this.getProjects()
            }
        },
        'selectProject': function(){
            var p = this.global.project
            console.log('project', p)
            if(!p || !p.name)
                this.global.project = this.projects[0]
        }
    }
})