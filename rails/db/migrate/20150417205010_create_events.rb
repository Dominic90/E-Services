class CreateEvents < ActiveRecord::Migration
  def change
    create_table :events do |t|
      t.string :title
      t.text :description
      t.references :user, index: true
      t.references :connection, index: true
      t.string :url

      t.timestamps null: false
    end
  end
end
