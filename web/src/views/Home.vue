<template>
    <a-layout>
      <a-layout-sider width="200" style="background: #fff">
        <a-menu
          mode="inline"
          :style="{ height: '100%', borderRight: 0 }"
          @click="handleClick"
        >
          <a-menu-item key="welcome">
            <MailOutlined/>
            <span>Welcome</span>
          </a-menu-item>
          <a-sub-menu v-for="item in level1" :key="item.id">
            <template v-slot:title>
              <span><bars-outlined />  {{item.name}}</span>
            </template>
            <a-menu-item v-for="child in item.children" :key="child.id">
              <span><book-outlined /> {{child.name}}</span>
            </a-menu-item>
          </a-sub-menu>
        </a-menu>
      </a-layout-sider>
      <a-layout-content
        :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
      >
        <!-- Content -->
        <div class="welcome" v-show="isWelcome">
          <h1>Welcome to Ebook Wiki!!</h1>
          <a-list
              item-layout="vertical"
              size="large"
              :grid= "{ gutter: 20, column: 3 }"
              :data-source="ebooks"
          >
            <template #renderItem="{ item }">
              <a-list-item key="item.name">
                <template #actions>
                <span v-for="{ type, text } in actions" :key="type">
                  <component :is="type" style="margin-right: 8px" />
                  {{ text }}
                </span>
                </template>
                <a-list-item-meta :description="item.description">
                  <template #title>
                    <router-link :to="'/doc?ebookId=' + item.id">
                      {{item.name}}
                      <!-- <a :href="item.href">{{ item.name }}</a> -->
                    </router-link>
                  </template>
                  <template #avatar><a-avatar :src="item.cover" /></template>
                </a-list-item-meta>
              </a-list-item>
            </template>

          </a-list>
        </div>
        <a-list 
          v-show="!isWelcome"
          item-layout="vertical" 
          size="large" 
          :grid= "{ gutter: 20, column: 3 }" 
          :data-source="ebooks"
        >
          <template #renderItem="{ item }">
            <a-list-item key="item.name">
              <template #actions>
                <span v-for="{ type, text } in actions" :key="type">
                  <component :is="type" style="margin-right: 8px" />
                  {{ text }}
                </span>
              </template>
              <a-list-item-meta :description="item.description">
                <template #title>
                  <router-link :to="'/doc?ebookId=' + item.id">
                    {{item.name}}
                    <!-- <a :href="item.href">{{ item.name }}</a> -->
                  </router-link>
                </template>
                <template #avatar><a-avatar :src="item.cover" /></template>
              </a-list-item-meta>
            </a-list-item>
          </template>
        </a-list>
      </a-layout-content>
    </a-layout>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref} from 'vue';
import axios from 'axios';
import { message } from 'ant-design-vue';
import {Tool} from "@/util/tool";

// const listData: any = [];

// for (let i = 0; i < 23; i++) {
//   listData.push({
//     href: 'https://www.antdv.com/',
//     title: `ant design vue part ${i}`,
//     avatar: 'https://joeschmoe.io/api/v1/random',
//     description:
//       'Ant Design, a design language for background applications, is refined by Ant UED Team.',
//     content:
//       'We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.',
//   });
// }

export default defineComponent({
  name: 'Home',
  setup(){
    // console.log("setup");
    // Write the initialziation function in onMounted function (life-cycle funtion)
    // If we write before onMounted, then sometimes it will attain before the website render
    // all these elements, which gives some errors.  

    // ref gives the reactive data that dynamic for ebook.
    const ebooks = ref();
    // Use reactive or ref. Both can help you get content. Need to use one of which in a project. 
    // Easy to maintain. 
    // const ebooks1 = reactive({books:[]});
    
    const level1 = ref();
    let categorys: any;
    /**
     * Query all categories. 
     * 
    */
    const handleQueryCategory=() => {
      axios.get("/category/all").then((response) =>{
        const data = response.data;
        if(data.success){
          categorys = data.content;
          console.log("Original array: ", categorys);

          level1.value = [];
          level1.value = Tool.array2Tree(categorys, 0);
          console.log("Tree structure: ", level1);
        }else {
          message.error(data.message);
        }
      });
    };

    const isWelcome = ref(true);
    let categoryId2 = 0;

    const handleQueryEbook = () => {
      if (!isWelcome.value) {
        axios.get("/ebook/list", {
          params: {
            page: 1,
            size: 100,
            categoryId2: categoryId2
          }
        }).then(function (response) {
          const data = response.data;
          ebooks.value = data.content.list;
          console.log("ebooks: ", ebooks);
          // ebooks1.books = data.content.list;
          // console.log(response); // We use axios.interceptor to print response.
          // We also delete setup and onMounted log.
        });
      }else{
        axios.get("/ebook/list", {
          params: {
            page: 1,
            size: 100,
          }
        }).then(function (response) {
          const data = response.data;
          ebooks.value = data.content.list;
          console.log("ebooks: ", ebooks);
          // ebooks1.books = data.content.list;
          // console.log(response); // We use axios.interceptor to print response.
          // We also delete setup and onMounted log.
        });
      }
    };

    const handleClick = (value: any) => {
      console.log("menu click", value)
      if(value.key === "welcome"){
        isWelcome.value = true;
        handleQueryEbook();
      }else{
        categoryId2 = value.key;
        isWelcome.value = false;
        handleQueryEbook();
      }
    };

    
    onMounted(() => {
      handleQueryCategory();
      handleQueryEbook();
      // console.log("onMounted1112");
      // function (response) {} is the same as (response) => {}
    })
    return {
      ebooks,
      // ebooks2: toRef(ebooks1, "books"),
      // listData,
      pagination: {
        onChange: (page: any) => {
          console.log(page);
        },
        pageSize: 3
      },
      actions:[
        { type: 'StarOutlined', text: '156' },
        { type: 'LikeOutlined', text: '156' },
        { type: 'MessageOutlined', text: '2' },
      ],

      handleClick,
      level1,

      isWelcome,
    }
  }
});
</script>

<style scoped>
  .ant-avatar {
    width: 50px;
    height: 50px;
    line-height: 50px;
    border-radius: 8%;
    margin: 5px 0;
  }
</style>

