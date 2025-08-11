<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="150px">
      <el-form-item label="关联题目" prop="questionId">
        <el-select v-model="queryParams.questionId" placeholder="请选择标签" clearable style="width: 200px">
            <el-option
              v-for="title in titleList"
              :key="title.id"
              :label="title.name"
              :value="title.id"
            />
        </el-select>
      </el-form-item>
      <el-form-item label="选项编码" prop="optionCode">
        <el-input
          v-model="queryParams.optionCode"
          placeholder="请输入选项编码"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否正确答案" prop="isCorrect">

        <el-select v-model="queryParams.isCorrect" placeholder="请选择是否正确答案" clearable style="width: 200px">
          <el-option
            v-for="dict in sys_questionoption_isno"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="是否部分正确" prop="isPartiallyCorrect">
        <el-select v-model="queryParams.isPartiallyCorrect" placeholder="请选择是否部分正确" clearable style="width: 200px">
          <el-option
            v-for="dict in sys_questionoption_isno"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="选项排序" prop="sort">
        <el-input
          v-model="queryParams.sort"
          placeholder="请输入选项排序"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="版本号" prop="version">
        <el-input
          v-model="queryParams.version"
          placeholder="请输入版本号"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['option:option:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['option:option:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['option:option:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['option:option:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="optionList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="选项主键ID" align="center" prop="id" />
      <el-table-column label="关联题目" align="center" prop="question" />
      <el-table-column label="选项编码" align="center" prop="optionCode" />
      <el-table-column label="选项内容" align="center" prop="optionContent" />
      <el-table-column label="是否正确答案" align="center" prop="isCorrect">
        <template #default="scope">
          <dict-tag :options="sys_questionoption_isno" :value="scope.row.isCorrect"/>
        </template>
      </el-table-column>
      <el-table-column label="分值权重" align="center" prop="scoreWeight" />
      <el-table-column label="是否部分正确" align="center" prop="isPartiallyCorrect">
        <template #default="scope">
          <dict-tag :options="sys_questionoption_isno" :value="scope.row.isPartiallyCorrect"/>
        </template>
      </el-table-column>
      <el-table-column label="选项排序" align="center" prop="sort" />
      <el-table-column label="选项组" align="center" prop="optionGroup" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="版本号" align="center" prop="version" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['option:option:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['option:option:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改题目选项（支持单选/多选/判断题，题目删除需级联删除选项）对话框 -->
    <el-dialog :title="title" v-model="open" width="60%" append-to-body>
      <el-form ref="optionRef" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="关联题目" prop="questionId">
            <el-select v-model="form.questionId" placeholder="请选择标签" clearable style="width: 200px">
            <el-option
              v-for="title in titleList"
              :key="title.id"
              :label="title.name"
              :value="title.id"
            />
        </el-select>
        </el-form-item>
        <el-form-item label="选项编码" prop="optionCode">
          <el-input v-model="form.optionCode" placeholder="请输入选项编码" />
        </el-form-item>
        <el-form-item label="选项内容">
          <editor v-model="form.optionContent" :min-height="192"/>
        </el-form-item>
        <el-form-item label="是否正确答案" prop="isCorrect">
          <el-radio-group v-model="form.isCorrect">
            <el-radio
              v-for="dict in sys_questionoption_isno"
              :key="dict.value"
              :label="parseInt(dict.value)"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="分值权重" prop="scoreWeight">
          <el-input v-model="form.scoreWeight" placeholder="请输入分值权重" />
        </el-form-item>
        <el-form-item label="是否部分正确" prop="isPartiallyCorrect">
          <el-select v-model="form.isPartiallyCorrect" placeholder="请选择是否部分正确">
            <el-option
              v-for="dict in sys_questionoption_isno"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="选项排序" prop="sort">
          <el-input-number v-model="form.sort" controls-position="right" :min="0" />
        </el-form-item>
        <el-form-item label="选项组" prop="optionGroup">
          <el-input v-model="form.optionGroup" placeholder="请输入选项组" />
        </el-form-item>
        <el-form-item label="备注">
          <editor v-model="form.remark" :min-height="192"/>
        </el-form-item>
        <el-form-item label="版本号" prop="version">
          <el-input v-model="form.version" placeholder="请输入版本号" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="Option">
import { listOption, getOption, delOption, addOption, updateOption } from "@/api/option/option"
import { listManage} from "@/api/manage/manage"
import { ref, reactive, toRefs, getCurrentInstance} from 'vue'



const { proxy } = getCurrentInstance()
const { sys_questionoption_isno } = proxy.useDict('sys_questionoption_isno')

const optionList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")
const titleList = ref([])


const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    questionId: null,
    optionCode: null,
    optionContent: null,
    isCorrect: null,
    isPartiallyCorrect: null,
    sort: null,
    version: null
  },
  rules: {
    questionId: [
      { required: true, message: "关联题目不能为空", trigger: "blur" }
    ],
    optionCode: [
      { required: true, message: "选项编码不能为空", trigger: "blur" }
    ],
    optionContent: [
      { required: true, message: "选项内容不能为空", trigger: "blur" }
    ],
    isCorrect: [
      { required: true, message: "是否正确答案：1-是 0-否不能为空", trigger: "change" }
    ],
    scoreWeight: [
      { required: true, message: "分值权重不能为空", trigger: "blur" }
    ],
    isPartiallyCorrect: [
      { required: true, message: "是否部分正确不能为空", trigger: "change" }
    ],
    sort: [
      { required: true, message: "选项排序不能为空", trigger: "blur" }
    ],
    createBy: [
      { required: true, message: "创建者不能为空", trigger: "blur" }
    ],
    createTime: [
      { required: true, message: "创建时间不能为空", trigger: "blur" }
    ],
  }
})

const { queryParams, form, rules } = toRefs(data)



// // 查询title列表
// const getTitleList = () => {
//   listManageTitle().then(response => {
//     if (response && response.code === 200) {
//       const data = response.data || [];
//       titleList.value = data.map((name, index) => ({
//         id: index + 1, // 假设 id 从 1 开始
//         name: name,
//       }));
//     } else {
//       titleList.value = [];
//     }
//   }).catch(error => {
//     console.error('获取类目列表失败:', error);
//     titleList.value = [];
//   });
// }


/** 查询tagNameList 列表 */
function getTitleList() {
  loading.value = true;
  listManage(queryParams.value).then(response => {
    titleList.value = response.rows.map(item => ({
      id: item.id,
      name: item.title || item.name  // 根据实际返回数据结构调整
    }));
    loading.value = false;
  }).catch(error => {
    console.error('获取标签列表失败:', error);
    titleList.value = [];
    loading.value = false;
  });
}



/** 查询题目选项（支持单选/多选/判断题，题目删除需级联删除选项）列表 */
function getList() {
  loading.value = true
  listOption(queryParams.value).then(response => {
    optionList.value = response.rows
    total.value = response.total
    loading.value = false
  })
}

// 取消按钮
function cancel() {
  open.value = false
  reset()
  resetQuery()
}

// 表单重置
function reset() {
  form.value = {
    id: null,
    questionId: null,
    optionCode: null,
    optionContent: null,
    isCorrect: null,
    scoreWeight: null,
    isPartiallyCorrect: null,
    sort: null,
    optionGroup: null,
    createBy: null,
    createTime: null,
    updateBy: null,
    updateTime: null,
    remark: null,
    version: null,
    id1: null
  }
  proxy.resetForm("optionRef")
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1
  getList()
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef")
  handleQuery()
}

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.id)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function handleAdd() {
  reset()
  open.value = true
  title.value = "添加题目选项"
}

// /** 修改按钮操作 */
// function handleUpdate(row) {
//   reset()
//   const _id = row.id || ids.value
//   getOption(_id).then(response => {
//     form.value = response.data
//     form.value.questionId = response.data.question
//     open.value = true
//     title.value = "修改题目选项"
    
//   })
// }


/** 修改按钮操作 */
function handleUpdate(row) {
  // 保存搜索栏当前的 questionId
  
  reset();
  const _id = row.id || ids.value;
  getOption(_id).then(response => {
    form.value = response.data;
    // form.value.id1 = form.value.questionId;
    // form.value.questionId = response.data.question; // 回显名称（临时）
    open.value = true;
    title.value = "修改题目选项";
  });
}




// /** 修改按钮操作 */
// function handleUpdate(row) {
//   reset();
//   const _id = row.id || ids.value;
//   getOption(_id).then(response => {
//     form.value = response.data;
//     // 假设 response.data.question 是题目名称，需要转成 ID
//     const targetTitle = titleList.value.find(item => item.name === response.data.question);
//     form.value.questionId = targetTitle ? targetTitle.id : null; // 赋值 ID
//     open.value = true;
//     title.value = "修改题目选项";
//   });
// }

/** 提交按钮 */
function submitForm() {
  proxy.$refs["optionRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateOption(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          reset()
          resetQuery()
          getList()
        })
      } else {
        addOption(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功")
          open.value = false
          reset()
          resetQuery()
          getList()
        })
      }
    }
  })
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _ids = row.id || ids.value
  proxy.$modal.confirm('是否确认删除题目选项（支持单选/多选/判断题，题目删除需级联删除选项）编号为"' + _ids + '"的数据项？').then(function() {
    return delOption(_ids)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('option/option/export', {
    ...queryParams.value
  }, `option_${new Date().getTime()}.xlsx`)
}

getList()
getTitleList()

</script>


<!-- 
<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="150px">
      <el-form-item label="关联题目" prop="questionId">
        
        <el-select 
          v-model="searchQuestionId" 
          placeholder="请选择关联题目" 
          clearable 
          style="width: 200px"
          @change="handleSearchQuestionChange"
        >
          <el-option
            v-for="title in titleList"
            :key="title.id"
            :label="title.name"
            :value="title.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="选项编码" prop="optionCode">
        <el-input
          v-model="queryParams.optionCode"
          placeholder="请输入选项编码"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否正确答案" prop="isCorrect">
        <el-select v-model="queryParams.isCorrect" placeholder="请选择是否正确答案" clearable style="width: 200px">
          <el-option
            v-for="dict in sys_questionoption_isno"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="是否部分正确" prop="isPartiallyCorrect">
        <el-select v-model="queryParams.isPartiallyCorrect" placeholder="请选择是否部分正确" clearable style="width: 200px">
          <el-option
            v-for="dict in sys_questionoption_isno"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="选项排序" prop="sort">
        <el-input
          v-model="queryParams.sort"
          placeholder="请输入选项排序"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="版本号" prop="version">
        <el-input
          v-model="queryParams.version"
          placeholder="请输入版本号"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['option:option:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['option:option:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['option:option:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['option:option:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="optionList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="选项主键ID" align="center" prop="id" />
      <el-table-column label="关联题目" align="center" prop="question" />
      <el-table-column label="选项编码" align="center" prop="optionCode" />
      <el-table-column label="选项内容" align="center" prop="optionContent" />
      <el-table-column label="是否正确答案" align="center" prop="isCorrect">
        <template #default="scope">
          <dict-tag :options="sys_questionoption_isno" :value="scope.row.isCorrect"/>
        </template>
      </el-table-column>
      <el-table-column label="分值权重" align="center" prop="scoreWeight" />
      <el-table-column label="是否部分正确" align="center" prop="isPartiallyCorrect">
        <template #default="scope">
          <dict-tag :options="sys_questionoption_isno" :value="scope.row.isPartiallyCorrect"/>
        </template>
      </el-table-column>
      <el-table-column label="选项排序" align="center" prop="sort" />
      <el-table-column label="选项组" align="center" prop="optionGroup" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="版本号" align="center" prop="version" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['option:option:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['option:option:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    
    <el-dialog :title="title" v-model="open" width="60%" append-to-body>
      <el-form ref="optionRef" :model="form" :rules="rules" label-width="65px">
        <el-form-item label="关联题目" prop="questionId">
          
          <el-select 
            v-model="form.questionId" 
            placeholder="请选择关联题目" 
            clearable 
            style="width: 200px"
          >
            <el-option
              v-for="title in titleList"
              :key="title.id"
              :label="title.name"
              :value="title.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="选项编码" prop="optionCode">
          <el-input v-model="form.optionCode" placeholder="请输入选项编码" />
        </el-form-item>
        <el-form-item label="选项内容">
          <editor v-model="form.optionContent" :min-height="192"/>
        </el-form-item>
        <el-form-item label="是否正确答案" prop="isCorrect">
          <el-radio-group v-model="form.isCorrect">
            <el-radio
              v-for="dict in sys_questionoption_isno"
              :key="dict.value"
              :value="parseInt(dict.value)"  
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="分值权重" prop="scoreWeight">
          <el-input v-model="form.scoreWeight" placeholder="请输入分值权重" />
        </el-form-item>
        <el-form-item label="是否部分正确" prop="isPartiallyCorrect">
          <el-select v-model="form.isPartiallyCorrect" placeholder="请选择是否部分正确">
            <el-option
              v-for="dict in sys_questionoption_isno"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="选项排序" prop="sort">
          <el-input v-model="form.sort" placeholder="请输入选项排序" />
        </el-form-item>
        <el-form-item label="选项组" prop="optionGroup">
          <el-input v-model="form.optionGroup" placeholder="请输入选项组" />
        </el-form-item>
        <el-form-item label="备注">
          <editor v-model="form.remark" :min-height="192"/>
        </el-form-item>
        <el-form-item label="版本号" prop="version">
          <el-input v-model="form.version" placeholder="请输入版本号" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="Option">
import { listOption, getOption, delOption, addOption, updateOption } from "@/api/option/option"
import { listManageTitle } from "@/api/manage/manage"
import { ref, reactive, toRefs, getCurrentInstance } from 'vue'


const { proxy } = getCurrentInstance()
const { sys_questionoption_isno } = proxy.useDict('sys_questionoption_isno')

// 核心：定义搜索栏独立变量
const searchQuestionId = ref(null)  // 搜索栏的关联题目ID

// 其他响应式变量
const optionList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")
const titleList = ref([])

// 表单数据
const data = reactive({
  form: {
    id: null,
    questionId: null,  // 弹窗的关联题目ID（与搜索栏分离）
    optionCode: null,
    optionContent: null,
    isCorrect: null,
    scoreWeight: null,
    isPartiallyCorrect: null,
    sort: null,
    optionGroup: null,
    createBy: null,
    createTime: null,
    updateBy: null,
    updateTime: null,
    remark: null,
    version: null
  },
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    questionId: null,  // 搜索参数的关联题目ID
    optionCode: null,
    optionContent: null,
    isCorrect: null,
    isPartiallyCorrect: null,
    sort: null,
    version: null
  },
  rules: {
    questionId: [
      { required: true, message: "关联题目不能为空", trigger: "blur" }
    ],
    optionCode: [
      { required: true, message: "选项编码不能为空", trigger: "blur" }
    ],
    optionContent: [
      { required: true, message: "选项内容不能为空", trigger: "blur" }
    ],
    isCorrect: [
      { required: true, message: "是否正确答案不能为空", trigger: "change" }
    ],
    scoreWeight: [
      { required: true, message: "分值权重不能为空", trigger: "blur" }
    ],
    isPartiallyCorrect: [
      { required: true, message: "是否部分正确不能为空", trigger: "change" }
    ],
    sort: [
      { required: true, message: "选项排序不能为空", trigger: "blur" }
    ]
  }
})

const { queryParams, form, rules } = toRefs(data)

// 查询题目列表（用于下拉选择）
const getTitleList = () => {
  listManageTitle().then(response => {
    if (response && response.code === 200) {
      const data = response.data || [];
      // 确保id与后端存储的questionId一致
      titleList.value = data.map((item, index) => ({
        id: item.id || index + 1,  // 优先使用后端返回的id
        name: item.name || item    // 兼容字符串数组或对象数组
      }));
    } else {
      titleList.value = [];
    }
  }).catch(error => {
    console.error('获取题目列表失败:', error);
    titleList.value = [];
  });
}

/** 查询选项列表 */
function getList() {
  loading.value = true
  listOption(queryParams.value).then(response => {
    optionList.value = response.rows
    total.value = response.total
    loading.value = false
  })
}

// 搜索栏选择题目后同步到查询参数
const handleSearchQuestionChange = () => {
  queryParams.value.questionId = searchQuestionId.value;
}

// 取消按钮
function cancel() {
  open.value = false
  reset()
}

// 表单重置
function reset() {
  form.value = {
    id: null,
    questionId: null,
    optionCode: null,
    optionContent: null,
    isCorrect: null,
    scoreWeight: null,
    isPartiallyCorrect: null,
    sort: null,
    optionGroup: null,
    remark: null,
    version: null
  }
  proxy.resetForm("optionRef")
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1
  getList()
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef")
  searchQuestionId.value = null;  // 重置搜索栏变量
  handleQuery()
}

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.id)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function handleAdd() {
  reset()
  open.value = true
  title.value = "添加题目选项"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _id = row.id || ids.value
  getOption(_id).then(response => {
    const resData = response.data;
    form.value = { ...resData };
    // 关键：确保回显的是题目ID（不是名称）
    // 如果后端返回的是question名称，需要转换为ID
    if (resData.question && !resData.questionId) {
      const target = titleList.value.find(item => item.name === resData.question);
      form.value.questionId = target ? target.id : null;
    } else {
      form.value.questionId = resData.questionId;  // 直接使用ID
    }
    open.value = true
    title.value = "修改题目选项"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["optionRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateOption(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addOption(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功")
          open.value = false
          getList()
        })
      }
    }
  })
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _ids = row.id || ids.value
  proxy.$modal.confirm(`是否确认删除编号为"${_ids}"的选项？`).then(function() {
    return delOption(_ids)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('option/option/export', {
    ...queryParams.value
  }, `option_${new Date().getTime()}.xlsx`)
}

// 初始化加载
getList()
getTitleList()
</script> -->
