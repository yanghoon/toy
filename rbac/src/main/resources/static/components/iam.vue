<template>
  <v-row justify="center">
    <v-col sm="10">
      <h2>IAM</h2>

      <v-row>
        <v-col sm="3" class="blue-grey--text text--darken-3">
          <v-autocomplete
            label="+ Invite User"
            hint="Press 'ctrl + enter' to invite user"
            v-model="form.selected"
            clearable
            hide-no-data
            return-object
            :disabled="endpoints.invalid"
            :item-text="form.itemKey"
            :items="form.items"
            :loading="form.loading"
            :search-input.sync="form.search"
            @change="form.selectd = ''"
            @keyup.ctrl.enter="inviteUser">
            <!-- @keydown.esc="resetForm" -->
          </v-autocomplete>
        </v-col>
      </v-row>

      <v-simple-table class="blue-grey darken-3">
        <template v-slot:default>
          <thead class="blue-grey darken-4">
            <tr>
              <th class="text-left">Name</th>
              <th class="text-left">Email</th>
              <th class="text-left">Actions</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="item in items" :key="item.name">
              <td>{{ item.name }}</td>
              <td>{{ item.email }}</td>
              <td>
                <v-btn small icon class="blue-grey--text" @click="leaveUser(item)">
                  <v-icon>mdi-delete</v-icon>
                </v-btn>
              </td>
            </tr>
          </tbody>
        </template>
      </v-simple-table>

      <v-progress-linear indeterminate v-if="loading"></v-progress-linear>
    </v-col>
  </v-row>
</template>

<script>
// https://github.com/FranckFreiburger/http-vue-loader/issues/34#issuecomment-375082104
module.exports = {
    data: function() {
      return {
        items: [],
        loading: false,
        form: {
          newProjName: '',
          selected: '',
          items: [],
          loading: false,
          search: null,
          itemKey: 'name'
        }
      }
    },
    computed: {
      'endpoints': function(){
        // https://github.com/vuejs/vue/issues/1964#issuecomment-162210972
        // console.log('endpoint_members.computed', global.realm, global.project) 
        var r = global.realm || {}, p = global.project || {}
        return {
          users: '/companies/' + r.name + '/users',
          members: '/companies/' + r.name + '/projects/' + p.id + '/members',
          invalid: !p.id
        }
      }
    },
    watch: {
      'form.search': function(val) {
        // console.log('keyword is changed : ', val)
        var selected = this.form.selected
        if(!val || val && val !== (selected && selected.name))
          this.searchUser(val)
      },
      'endpoints.members': function(nval, oval){
        // console.log('endpoint_members.watch', oval, nval)
        nval && this.getUserList()
      },
      'endpoints.invalid': function(nval, oval){
        if(nval == true){
          this.items = []
          this.form.search = ''
        }
      }
    },
    methods: {
      'getUserList': function(){
        if(this.endpoints.invalid) return;

        var vm = this
        var success = function(res){ vm.items = res.data; vm.loading = false; }
        var url = this.endpoints.members

        this.loading = true
        axios.get(url).then(success)
      },
      'searchUser': function(keyword){
        console.log('search user with keyword : ', keyword)

        var vm = this
        var success = function(res){ vm.form.items = res.data; vm.form.loading = false; }
        var url = this.endpoints.users + '?keyword=' + keyword

        this.form.loading = true
        axios.get(url).then(success)
      },
      'inviteUser': function(user){
        var user = this.form.selected
        var url = this.endpoints.members + '/' + user.id

        // this.form.selected = {}
        // this.$nextTick(() => { this.form.selected = {} })
        axios.put(url).then(this.getUserList)
      },
      'leaveUser': function(user){
        var url = this.endpoints.members + '/' + user.id
        axios.delete(url).then(this.getUserList)
      }
    }
  }
</script>