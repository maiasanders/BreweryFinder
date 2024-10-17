<template>
<div id="cat-selector">
    <p>Event Categories</p>
    <ul id="cat-options">
        <li v-for="category in unselectedCats" :key="category.id" @click="addToSelectedCategories(category)">{{ category.categoryName }}</li>
        <li @click="displayNewCatInput = !displayNewCatInput">Don't see what you're looking for? Add a new category!
            <font-awesome-icon icon="plus" />
        </li>
    </ul>
    <div id="new-cat" v-if="displayNewCatInput">
        <input type="text" id="new-cat-in" name="new-cat" v-model="newCategory" placeholder="New category name">
        <button @click="submitNewCategory">Add new category</button>
    </div>
    <ul id="selected-cats">
        <li v-for="category in newSelectedCategories" :key="category.id">{{ category.categoryName }}
            <font-awesome-icon icon="fa-regular fa-circle-xmark" @click="removeFromSelected(category.id)" />
        </li>
    </ul>
</div>
</template>

<script>
export default {
    props: {
        selectedCategories: {
            type: Array,
            required: true
        }
    },
    data() {
        return {
            // categoryOptions: [],
            newSelectedCategories: [],
            newCategory: '',
            displayNewCatInput: false

        }
    },
    computed: {
        selectedCatIds() {
            return this.newSelectedCategories.map(cat => cat.id)
        },
        unselectedCats() {
            return this.categoryOptions.filter(cat => !this.selectedCatIds.includes(cat.id));
        },
        categoryOptions() {
            return this.$store.getters.getCategories;
        }
    },
    methods: {
        removeFromSelected(id) {
            for (let i = 0; i < this.newSelectedCategories.length; i++) {
                if (this.newSelectedCategories[i].id === id) {
                    this.newSelectedCategories.splice(i, 1)
                }
            }
            this.$store.commit('UPDATE_SELECTED_CATEGORIES', this.newSelectedCategories)
        },
        addToSelectedCategories(category) {
            this.newSelectedCategories.push(category);
            this.$store.commit('UPDATE_SELECTED_CATEGORIES', this.newSelectedCategories)
        },
        submitNewCategory() {
            const newCat = {
                categoryName: this.newCategory
            }
            this.$store.dispatch('addNewCategory', newCat)
                .then(response => {
                    const newCat = response.data;
                    this.addToSelectedCategories(newCat)
                })
            this.newCategory = '';
            this.displayNewCatInput = false;
        }
    },
    created() {
        this.$store.dispatch('fetchCategories')

        this.newSelectedCategories = this.selectedCategories;
        // this.newSelectedCategories = [...this.selectedCategories];
    },
    mounted() {
        // this.categoryOptions = this.$store.getters.getCategories;
    }
}
</script>

<style scoped>
#cat-selector {
    /* display: flex;
    gap: 20px;
    align-items: start; */
    display: grid;
    grid-template-columns: 1fr 1fr;
    grid-template-areas: "title ." "options selected" "new selected";
    gap: 20px;
}

p {
    grid-area: title;
    margin: 0;
}

#cat-options {
    background-color: var(--nav-color);
    grid-area: options;
    padding: 10px;
    border: 1px solid var(--input-border-color);
    border-radius: 10px;
}

#selected-cats {
    /* background-color: var(--input-color); */
    grid-area: selected;
}

#new-cat {
    grid-area: new;
    display: flex;
    justify-content: space-between;
    gap: 10px;
}

#selected-cats>li {
    background-color: var(--input-color);
    border-left: 1px solid var(--input-border-color);
    border-right: 1px solid var(--input-border-color);
    padding: 5px 10px;
    &:first-child {
        border-radius: 10px 10px 0 0;
        border-top: 1px solid var(--input-border-color);
    }
    &:last-child {
        border-radius: 0 0 10px 10px;
        border-bottom: 1px solid var(--input-border-color);
    }
}
</style>