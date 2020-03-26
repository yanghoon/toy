<template>
  <v-row justify="start">
    <v-col sm="10" offset-sm="1">
      <v-btn small icon class="ml-n2 d-inline-flex blue-grey--text pb-1 text-none" to="/integrations" >
        <v-icon>mdi-arrow-left</v-icon>
      </v-btn>
      <h2 class="d-inline-flex">Integration: {{ query.name }}</h2>
      <v-btn text class="ml-1 d-inline-flex blue-grey--text text-none" @click="updateItem">
        <v-icon left>mdi-content-save</v-icon>
        Save
      </v-btn>
    </v-col>

    <v-col sm="5" offset-sm="1">
      <v-select label="Kind *" :items="fields.kind" v-model="tool.kind" :disabled="query.name"></v-select>
      <v-text-field label="Name *" counter="30" v-model="tool.name" :disabled="query.name"></v-text-field>
      <v-text-field label="Endpoint *"
          persistent-hint
          hint="https://oidc.tool.com"
          v-model="tool.endpoint">
      </v-text-field>

        <v-data-table
          :headers="table.headers"
          :items="table.items"
          hide-default-footer
          class="blue-grey darken-3 mt-10 elevation-1">
          <template v-slot:top>
            <div class="px-4 pt-3 d-flex justify-space-between">
              <h3 class="d-inline-flex">Attributes</h3>

              <v-btn text class="d-inline-flex blue-grey--text text-none" @click="table.items.push({key:'', val:''})">
                <v-icon>mdi-plus</v-icon>
                <span class="body-2 font-weight-bold">Add</span>
              </v-btn>
            </div>
          </template>

          <template v-slot:item.key="{item}">
            <v-edit-dialog>
              {{ item.key }}
              <span v-if="!item.key" class="blue-grey--text">Click to edit</span>
              <template v-slot:input>
                <v-text-field v-model="item.key" class="mb-3" persistent-hit hint="Type and Press enter to save" single-line></v-text-field>
              </template>
            </v-edit-dialog>
          </template>

          <template v-slot:item.value="{item}">
            <v-edit-dialog>
              {{ item.value }}
              <span v-if="!item.value" class="blue-grey--text">Click to edit</span>
              <template v-slot:input>
                <v-text-field v-model="item.value" class="mb-3" persistent-hit hint="Type and Press enter to save" single-line></v-text-field>
              </template>
            </v-edit-dialog>
          </template>

        </v-data-table>
    </v-col>

  </v-row>
</template>

<script>
// https://github.com/FranckFreiburger/http-vue-loader/issues/34#issuecomment-375082104
module.exports = {
    data: function() {
      return {
        table: {
          headers: [
            {text: 'Key', value: 'key'},
            {text: 'Value', value: 'value'},
          ],
          items: []
        },
        fields: {
          kind: ['Grafana', 'Jenkins', 'Habor', 'Azure AD']
        },
        tool: {},
        loading: false,
      }
    },
    computed: {
      'endpoints': function(){
        // https://github.com/vuejs/vue/issues/1964#issuecomment-162210972
        var r = global.realm || {}
        return {
          tool_new: '/companies/' + r.name + '/integrations/new',
          tool: '/companies/' + r.name + '/integrations/' + this.query.name,
          invalid: !r.name && !this.query.name
        }
      },
      'query': function(){
        // https://github.com/vuejs/vue-router/blob/dev/examples/route-props/app.js
        // https://hyeooona825.tistory.com/44
        return this.$route.query
      },
      'tool_attr': function(){
        // return [ {key: '', val: 'val'} ]
        return this.tool.attributes ? Object.entries(this.tool.attributes).map(([key, value]) => ({key,value})) : [];
      }
    },
    watch: {
      'endpoints.tool': function(url){
        if(!this.endpoints.invalid)
          this.getItem()
      }
    },
    methods: {
      'getItem': function(){
        var vm = this
        var success = function(res){
          vm.tool = res.data;
          vm.table.items = Object.entries(res.data.attributes).map(([key, value]) => ({key,value}))
          vm.loading = false;
        }
        var url = this.endpoints.tool

        this.loading = true
        axios.get(url).then(success)
      },
      'updateItem': function(){
        var url = this.query.name ? this.endpoints.tool : this.endpoints.tool_new
        var method = this.query.name ? axios.put : axios.post

        this.loading = true
        this.tool.attributes = this.table.items.reduce( (acc, val) => { acc[val.key] = val.value; return acc }, {})
        method(url, this.tool).then(this.getItem)
      }
    }
  }
</script>