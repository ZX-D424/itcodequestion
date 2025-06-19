
<template>

  <!--导航栏-->
  <nav class="navbar" >
    <div class="logo">
      <a  @click="router.push({path : '/homePage'})" target="_blank">
        <img src="../assets/homePage/images/nebulas.png" width="80px" height="60px" alt="NeBuLas">
      </a>
    </div>
    <div class="nav-links">
      <template  v-for="item in menuDataList" >
        <a href="#">{{item.name}}</a>
      </template>
    </div>
  </nav>

  <!-- 漂浮图标 -->
  <div class="floating-icons">
    <a href="#" class="feedback-icon">反馈</a>
    <a href="#" class="business-icon">合作</a>
    <a href="#" class="consult-icon">咨询</a>
  </div>

  <section class="section-html">
    <h2>HTML实战</h2>
    <div class="cards">

      <div class="card">
        <h3>HTML实战</h3>
        <p>整理10到50个常用标签。以及详解说明。让你快速通过HTML实战。</p>
      </div>

      <div class="card">
        <h3>HTML基础闯关</h3>
        <p>整理100道基础题，设置了100个关卡。1个题目1分，满分100分。让你快速掌握HTML基础。未来的码神。快来闯关吧。
          <br/><br/>
          <a href="#">开始闯关</a>
        </p>
      </div>

      <div class="card">
        <h3>XHTML实战</h3>
        <p>整理10到50个常用标签。以及详解说明。让你快速通过XHTML实战。</p>
      </div>
      <div class="card">
        <h3>XHTML基础闯关</h3>
        <p>整理100道基础题，设置了100个关卡。1个题目1分，满分100分。让你快速掌握XHTML基础。未来的码神。快来闯关吧。
          <br/><br/>
          <a href="#">开始闯关</a>
        </p>
      </div>

      <div class="card">
        <h3>HTML5实战</h3>
        <p>整理10到50个常用标签。以及详解说明。让你快速通过HTML5实战。</p>
      </div>
      <div class="card">
        <h3>HTML5基础闯关</h3>
        <p>整理100道基础题，设置了100个关卡。1个题目1分，满分100分。让你快速掌握HTML5基础。未来的码神。快来闯关吧。
          <br/><br/>
          <a href="#">开始闯关</a>
        </p>
      </div>
    </div>
  </section>


  <section class="section-css">
    <h2>CSS实战</h2>
    <div class="cards">
      <div class="card">
        <h3>CSS实战</h3>
        <p>整理10到50个常用标签。以及详解说明。让你快速通过CSS实战。</p>
      </div>
      <div class="card">
        <h3>CSS+HTML5基础闯关</h3>
        <p>整理100道基础题，设置了100个关卡。1个题目1分，满分100分。让你快速掌握CSS+HTML5基础。未来的码神。快来闯关吧。
          <br/><br/>
          <a href="#">开始闯关</a>
        </p>
      </div>

      <div class="card">
        <h3>CSS3实战</h3>
        <p>整理10到50个常用标签。以及详解说明。让你快速通过CSS3实战。</p>
      </div>
      <div class="card">
        <h3>CSS3+HTML5基础闯关</h3>
        <p>整理100道基础题，设置了100个关卡。1个题目1分，满分100分。让你快速掌握CSS3+HTML5基础。未来的码神。快来闯关吧。
          <br/><br/>
          <a href="#">开始闯关</a>
        </p>
      </div>
    </div>
  </section>



  <section class="section-js">
    <h2>JavaScript实战</h2>
    <div class="cards">
      <div class="card">
        <h3>JavaScript实战</h3>
        <p>为全球500强企业打造智能化系统，实现全面数字化转型与业务升级。</p>
      </div>
      <div class="card">
        <h3>JavaScript+CSS+HTML5基础闯关</h3>
        <p>为全球500强企业打造智能化系统，实现全面数字化转型与业务升级。
          <br/><br/>
          <a href="#">开始闯关</a>
        </p>
      </div>

      <div class="card">
        <h3>Jquery实战</h3>
        <p>构建高性能大数据平台，助力客户发掘数据背后的商业潜能。</p>
      </div>
      <div class="card">
        <h3>Jquery+CSS3+HTML5基础闯关</h3>
        <p>打造安全、稳定的云计算环境，确保客户业务高效运转与数据安全。
          <br/><br/>
          <a href="#">开始闯关</a>
        </p>
      </div>

      <div class="card">
        <h3>Vue2实战</h3>
        <p>构建高性能大数据平台，助力客户发掘数据背后的商业潜能。</p>
      </div>
      <div class="card">
        <h3>Vue2+elment-ui基础闯关</h3>
        <p>打造安全、稳定的云计算环境，确保客户业务高效运转与数据安全。
          <br/><br/>
          <a href="#">开始闯关</a>
        </p>
      </div>

      <div class="card">
        <h3>Vue3实战</h3>
        <p>构建高性能大数据平台，助力客户发掘数据背后的商业潜能。</p>
      </div>

      <div class="card">
        <h3>Vue3+AntDesign基础闯关</h3>
        <p>打造安全、稳定的云计算环境，确保客户业务高效运转与数据安全。
          <br/><br/>
          <a href="#">开始闯关</a>
        </p>
      </div>

      <div class="card">
        <h3>React实战</h3>
        <p>构建高性能大数据平台，助力客户发掘数据背后的商业潜能。</p>
      </div>

      <div class="card">
        <h3>React+AntDesign基础闯关</h3>
        <p>打造安全、稳定的云计算环境，确保客户业务高效运转与数据安全。
          <br/><br/>
          <a href="#">开始闯关</a>
        </p>
      </div>

      <div class="card">
        <h3>React+elment-ui基础闯关</h3>
        <p>打造安全、稳定的云计算环境，确保客户业务高效运转与数据安全。
          <br/><br/>
          <a href="#">开始闯关</a>
        </p>
      </div>


      <div class="card">
        <h3>three.js实战</h3>
        <p>打造安全、稳定的云计算环境，确保客户业务高效运转与数据安全。</p>
      </div>

      <div class="card">
        <h3>three.js基础闯关</h3>
        <p>打造安全、稳定的云计算环境，确保客户业务高效运转与数据安全。

          <br/><br/>
          <a href="#">开始闯关</a>
        </p>
      </div>


      <div class="card">
        <h3>uniapp实战</h3>
        <p>打造安全、稳定的云计算环境，确保客户业务高效运转与数据安全。</p>
      </div>

      <div class="card">
        <h3>uniapp+vue2基础闯关</h3>
        <p>打造安全、稳定的云计算环境，确保客户业务高效运转与数据安全。
          <br/><br/>
          <a href="#">开始闯关</a>
        </p>
      </div>

      <div class="card">
        <h3>uniapp+vue3基础闯关</h3>
        <p>打造安全、稳定的云计算环境，确保客户业务高效运转与数据安全。
          <br/><br/>
          <a href="#">开始闯关</a>
        </p>
      </div>

      <div class="card">
        <h3>flutter实战</h3>
        <p>打造安全、稳定的云计算环境，确保客户业务高效运转与数据安全。</p>
      </div>
      <div class="card">
        <h3>flutter基础闯关</h3>
        <p>打造安全、稳定的云计算环境，确保客户业务高效运转与数据安全。
          <br/><br/>
          <a href="#">开始闯关</a>
        </p>
      </div>

    </div>
  </section>


  <section class="section-java">
    <h2>JAVA实战</h2>
    <div class="cards">
      <div class="card">
        <h3>JAVA实战</h3>
        <p>整理10到50个经典练习题。让你快速学习JAVA编程。</p>
      </div>
      <div class="card">
        <h3>JAVA基础闯关</h3>
        <p>整理100道基础题，设置了100个关卡。1个题目1分，满分100分。让你快速掌握java基础语法。未来的码神。快来闯关吧。
          <br/><br/>
          <a href="#">开始闯关</a>
        </p>
      </div>
      <div class="card">
        <h3>JAVA-jdbc实战</h3>
        <p>整理10到50个经典练习题。让你快速学习JAVA一jdbc编程。</p>
      </div>
      <div class="card">
        <h3>JAVA-jdbc基础闯关</h3>
        <p>整理100道基础题，设置了100个关卡。1个题目1分，满分100分。让你快速掌握JAVA一jdbc基础语法。未来的码神。快来闯关吧。
          <br/><br/>
          <a href="#">开始闯关</a>
        </p>
      </div>

      <div class="card">
        <h3>JAVA一Web-servlet实战</h3>
        <p>整理10到50个经典练习题。让你快速学习JAVA一Web-servlet编程。</p>
      </div>
      <div class="card">
        <h3>JAVA一Web-servlet基础闯关</h3>
        <p>整理100道基础题，设置了100个关卡。1个题目1分，满分100分。让你快速掌握JAVA一Web-servlet基础语法。未来的码神。快来闯关吧。
          <br/><br/>
          <a href="#">开始闯关</a>
        </p>
      </div>


      <div class="card">
        <h3>JAVA一spring mvc实战</h3>
        <p>整理10到50个经典练习题。让你快速学习JAVA一Web-servlet编程。</p>
      </div>
      <div class="card">
        <h3>JAVA一spring mvc-jdbc-html基础闯关</h3>
        <p>整理100道基础题，设置了100个关卡。1个题目1分，满分100分。让你快速掌握JAVA一spring mvc-jdbc-html基础语法。未来的码神。快来闯关吧。
          <br/><br/>
          <a href="#">开始闯关</a>
        </p>
      </div>

      <div class="card">
        <h3>JAVA一spring mvc - mybatis 实战</h3>
        <p>整理10到50个经典练习题。让你快速学习JAVA一Web-servlet编程。</p>
      </div>
      <div class="card">
        <h3>JAVA一spring mvc-mybatis-html基础闯关</h3>
        <p>整理100道基础题，设置了100个关卡。1个题目1分，满分100分。让你快速掌握JAVA一spring mvc-mybatis-html基础语法。未来的码神。快来闯关吧。
          <br/><br/>
          <a href="#">开始闯关</a>
        </p>
      </div>

      <div class="card">
        <h3>JAVA一spring boot - mybatis 实战</h3>
        <p>整理10到50个经典练习题。让你快速学习JAVA一Web-servlet编程。</p>
      </div>
      <div class="card">
        <h3>JAVA一spring boot-mybatis-html基础闯关</h3>
        <p>整理100道基础题，设置了100个关卡。1个题目1分，满分100分。让你快速掌握JAVA一spring boot-mybatis-html基础语法。未来的码神。快来闯关吧。
          <br/><br/>
          <a href="#">开始闯关</a>
        </p>
      </div>

      <div class="card">
        <h3>JAVA一spring boot - mybatis-plus 实战</h3>
        <p>整理10到50个经典练习题。让你快速学习JAVA一Web-servlet编程。</p>
      </div>
      <div class="card">
        <h3>JAVA一spring boot-mybatis-plus-html基础闯关</h3>
        <p>整理100道基础题，设置了100个关卡。1个题目1分，满分100分。让你快速掌握JAVA一spring boot-mybatis-plus-html基础语法。未来的码神。快来闯关吧。
          <br/><br/>
          <a href="#">开始闯关</a>
        </p>
      </div>

      <div class="card">
        <h3>JAVA一spring boot - mybatis-plus -vue2实战</h3>
        <p>整理10到50个经典练习题。让你快速学习JAVA一Web-servlet编程。</p>
      </div>
      <div class="card">
        <h3>JAVA一spring boot-mybatis-plus-vue2基础闯关</h3>
        <p>整理100道基础题，设置了100个关卡。1个题目1分，满分100分。让你快速掌握JAVA一spring boot-mybatis-plus-vue2基础语法。未来的码神。快来闯关吧。
          <br/><br/>
          <a href="#">开始闯关</a>
        </p>
      </div>

      <div class="card">
        <h3>JAVA一spring boot - mybatis-plus -vue3实战</h3>
        <p>整理10到50个经典练习题。让你快速学习JAVA一Web-servlet编程。</p>
      </div>
      <div class="card">
        <h3>JAVA一spring boot-mybatis-plus-vue3基础闯关</h3>
        <p>整理100道基础题，设置了100个关卡。1个题目1分，满分100分。让你快速掌握JAVA一spring boot-mybatis-plus-vue3基础语法。未来的码神。快来闯关吧。
          <br/><br/>
          <a href="#">开始闯关</a>
        </p>
      </div>

    </div>
  </section>


</template>


<script setup >


import {getMenuDataList} from "@/api/www/menu"


const route = useRoute()
const router = useRouter();
const menuDataList = ref([]);

//加载菜单
function initMenuDataList() {
  getMenuDataList().then(response => {
    menuDataList.value = response.data;
  });
}

function toModuleList(){

}

initMenuDataList();
</script>

<style  >
@import  "@/assets/homePage/css/project.css";
</style>
