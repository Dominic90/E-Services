class CreateFeeds < ActiveRecord::Migration
  def change
    create_table :feeds do |t|
      t.references :event, index: true
      t.string :title
      t.references :user, index: true
      t.string :url
      t.integer :connection_id

      t.timestamps null: false
    end
    add_foreign_key :feeds, :events
    add_foreign_key :feeds, :users
  end
end
