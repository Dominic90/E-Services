class CreateStreams < ActiveRecord::Migration
  def change
    create_table :streams do |t|
      t.references :feed, index: true
      t.string :url

      t.timestamps null: false
    end
    add_foreign_key :streams, :feeds
  end
end
