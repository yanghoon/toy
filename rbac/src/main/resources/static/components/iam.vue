<template>
  <v-row justify="center">
    <v-col sm="10">
      <h2>IAM</h2>

      <v-row>
        <v-col sm="3" class="blue-grey--text text--darken-3">
          <!--
          <v-text-field dense
              label="+ Add User"
              hint="Press enter to create"
              v-model="form.newProjName"
              @keyup.enter="addProj">
          </v-text-field>
          -->
          <v-autocomplete
            label="+ Invite User"
            hint="Press 'ctrl + enter' to invite user"
            v-model="form.selected"
            clearable
            hide-no-data
            return-object
            :item-text="form.itemKey"
            :items="form.items"
            :loading="form.loading"
            :search-input.sync="form.search"
            @change="form.selectd = ''"
            @keyup.ctrl.enter="inviteUser"
            >
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
        loading: true,
        realm: 'dummy', // 'bcda6199-1e96-4a34-9b0e-428a97f71e58',
        project: '4831f6d6-817a-4846-be47-daa45945842b', // 'proj-a'
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
    beforeMount: function() {
      this.getUserList();
    },
    watch: {
      'form.search': function(val) {
        console.log('keyword is changed : ', val)
        var selected = this.form.selected
        if(!val || val && val !== (selected && selected.name))
          this.searchUser(val)
      },
    },
    methods: {
      'getUserList': function(){
        var vm = this
        var success = function(res){ vm.items = res.data; vm.loading = false; }
        var url = '/companies/' + this.realm + '/projects/' + this.project + '/members'

        this.loading = true
        axios.get(url).then(success)
      },
      'searchUser': function(keyword){
        console.log('search user with keyword : ', keyword)

        var vm = this
        var success = function(res){ vm.form.items = res.data; vm.form.loading = false; }
        var url = '/companies/' + this.realm + '/users?keyword=' + keyword

        this.form.loading = true
        axios.get(url).then(success)
      },
      'inviteUser': function(user){
        var user = this.form.selected
        var url = '/companies/' + this.realm + '/projects/' + this.project + '/members/' + user.id

        // this.form.selected = {}
        // this.$nextTick(() => { this.form.selected = {} })
        axios.put(url).then(this.getUserList)
      },
      'leaveUser': function(user){
        var url = '/companies/' + this.realm + '/projects/' + this.project + '/members/' + user.id
        axios.delete(url).then(this.getUserList)
      },
      // 'resetForm': function() {
      //   console.log('reset!!')
      // }
      // 'addUser': function(){
      //   var url = '/companies/' + this.realm + '/projects/new?project=' + this.form.newProjName;

      //   this.form.newProjName = ''
      //   axios.post(url).then(this.getUserList)
      // },
      // 'removeUser': function(item){
      //   var url = '/companies/' + this.realm + '/projects/' + item.name;

      //   axios.delete(url).then(this.getUserList)
      // },
    }
  }
</script>